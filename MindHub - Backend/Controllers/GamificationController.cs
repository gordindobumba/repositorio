using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MindHub.Infrastructure.Data;
using MindHub.Domain.Models;
using MindHub.Services;

namespace MindHub.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class GamificationController : ControllerBase
    {
        private readonly AppDbContext _context;
        private readonly GamificationService _gamificationService;

        public GamificationController(AppDbContext context, GamificationService gamificationService)
        {
            _context = context;
            _gamificationService = gamificationService;
        }

        // Endpoint para ver o perfil, pontos e medalhas
        [HttpGet("profile")]
        public async Task<IActionResult> GetProfile()
        {
            var user = await _context.UserProfiles.FindAsync(1);
            if (user == null) return NotFound("Usuário não inicializado.");

            var achievements = await _context.UserAchievements
                .Where(ua => ua.UserProfileId == 1)
                .Include(ua => ua.Achievement)
                .Select(ua => new 
                {
                    ua.Achievement!.Title,
                    ua.Achievement!.Description,
                    ua.Achievement!.IconName,
                    EarnedDate = ua.UnlockedAt
                })
                .ToListAsync();

            return Ok(new
            {
                User = user.Name,
                TotalPoints = user.CurrentPoints,
                Achievements = achievements
            });
        }

        // --- ENDPOINT DE TESTE (CORRIGIDO) ---
        [HttpPost("test/seed")]
        public async Task<IActionResult> SeedTestData()
        {
            var user = await _context.UserProfiles.FindAsync(1);
            if (user == null) return NotFound("Usuário não encontrado.");

            // 1. Busca hábitos REAIS do banco para evitar erro de Foreign Key
            var habits = await _context.Habits.ToListAsync();
            
            // Se não tiver nenhum hábito criado, avisa o usuário em vez de quebrar
            if (habits.Count == 0) return BadRequest("Erro: Crie pelo menos 1 hábito antes de rodar o teste.");

            // Pega o ID do primeiro hábito que existir no seu banco
            var realHabitId = habits.First().Id; 

            // 2. Limpa dados antigos para resetar o teste (evita duplicidade)
            var oldCheckIns = await _context.CheckIns.ToListAsync();
            _context.CheckIns.RemoveRange(oldCheckIns);
            
            var oldAchievements = await _context.UserAchievements.ToListAsync();
            _context.UserAchievements.RemoveRange(oldAchievements);
            
            user.CurrentPoints = 0;
            await _context.SaveChangesAsync();

            // 3. Simula dados para "Mestre da Semana" (21 check-ins nos últimos 7 dias)
            var today = DateOnly.FromDateTime(DateTime.UtcNow);
            
            for (int i = 0; i < 7; i++)
            {
                // Adiciona 3 check-ins por dia usando o ID REAL que encontramos
                _context.CheckIns.Add(new HabitCheckIn { HabitId = realHabitId, Date = today.AddDays(-i) });
                _context.CheckIns.Add(new HabitCheckIn { HabitId = realHabitId, Date = today.AddDays(-i) });
                _context.CheckIns.Add(new HabitCheckIn { HabitId = realHabitId, Date = today.AddDays(-i) });
            }

            // 4. Simula dados para "Lenda Mensal" (30 dias seguidos no mesmo hábito)
            for (int i = 0; i < 30; i++)
            {
                var date = today.AddDays(-i);
                
                // Verifica na memória (Local) se já adicionamos para não duplicar no Save
                // (Porque o loop anterior de 7 dias já adicionou alguns check-ins nessas datas)
                bool exists = _context.CheckIns.Local.Any(c => c.HabitId == realHabitId && c.Date == date);
                
                if (!exists)
                {
                    _context.CheckIns.Add(new HabitCheckIn { HabitId = realHabitId, Date = date });
                }
            }

            await _context.SaveChangesAsync();

            // 5. Força o processamento para ganhar as medalhas
            // O sistema vai "ler" esses dados falsos e te dar os prêmios
            await _gamificationService.ProcessCheckInAsync(1);

            return Ok($"Sucesso! Dados gerados usando o Hábito ID {realHabitId}. Verifique o perfil.");
        }
    }
}