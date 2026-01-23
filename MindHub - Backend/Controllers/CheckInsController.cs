using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MindHub.Application.DTOs;
using MindHub.Domain.Models;
using MindHub.Infrastructure.Data;
using MindHub.Services; 

namespace MindHub.API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class CheckInsController : ControllerBase
    {
        private readonly AppDbContext _context;
        private readonly GamificationService _gamificationService;

        public CheckInsController(AppDbContext context, GamificationService gamificationService)
        {
            _context = context;
            _gamificationService = gamificationService;
        }

        [HttpGet]
        public async Task<IActionResult> GetDailyList([FromQuery] DateOnly date)
        {
            var dayOfWeek = date.DayOfWeek;

            var allHabits = await _context.Habits.ToListAsync();

            var habitsForToday = allHabits.Where(h => 
                h.Frequency == FrequencyType.Daily || 
                h.Frequency == FrequencyType.TimesPerWeek || 
                (h.Frequency == FrequencyType.SpecificDays && h.SpecificDays != null && h.SpecificDays.Contains(dayOfWeek))
            ).ToList();

            var checkIns = await _context.CheckIns
                .Where(c => c.Date == date)
                .Select(c => c.HabitId)
                .ToListAsync();

            var result = habitsForToday.Select(h => new DailyHabitDto
            {
                HabitId = h.Id,
                Title = h.Title,
                IsCompleted = checkIns.Contains(h.Id)
            });

            return Ok(result);
        }

        [HttpPost("toggle")]
        public async Task<IActionResult> ToggleCheckIn([FromBody] ToggleCheckInDto dto)
        {
            var today = DateOnly.FromDateTime(DateTime.UtcNow);
            if (dto.Date > today)
            {
                return BadRequest("Não é permitido concluir hábitos em datas futuras.");
            }

            var habit = await _context.Habits.FindAsync(dto.HabitId);
            if (habit == null) return NotFound("Hábito não encontrado.");

            // Verifica se o check-in JÁ existe
            var existingCheckIn = await _context.CheckIns
                .FirstOrDefaultAsync(c => c.HabitId == dto.HabitId && c.Date == dto.Date);

            if (existingCheckIn != null)
            {
                // DESMARCAR (Uncheck)
                _context.CheckIns.Remove(existingCheckIn);
                
                // Remove os pontos e salva
                await _gamificationService.RemovePointsAsync(1); 
                
                // Nota: O RemovePointsAsync já dá SaveChanges, mas garantimos que a remoção do checkin seja salva também.
                // Como compartilham o mesmo Contexto, o SaveChanges do serviço salva tudo que está pendente.
                
                return Ok(new { message = "Hábito desmarcado. Pontos estornados.", isCompleted = false });
            }
            else
            {
                // MARCAR (Check)
                var newCheckIn = new HabitCheckIn
                {
                    HabitId = dto.HabitId,
                    Date = dto.Date,
                    CompletedAt = DateTime.UtcNow
                };

                _context.CheckIns.Add(newCheckIn);
                await _context.SaveChangesAsync(); // Salva o CheckIn primeiro

                // Calcula pontos e emblemas
                await _gamificationService.ProcessCheckInAsync(1); 

                return Ok(new { message = "Hábito concluído! Pontos creditados.", isCompleted = true });
            }
        }
    }
}