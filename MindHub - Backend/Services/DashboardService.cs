using MindHub.Infrastructure.Data;
using MindHub.DTOs;
using MindHub.Domain.Models;
using Microsoft.EntityFrameworkCore;

namespace MindHub.Services
{
    public class DashboardService
    {
        private readonly AppDbContext _context;

        public DashboardService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<DashboardDto> GetDashboardAsync(int userId, DateTime start, DateTime end)
        {
            // Conversão de datas para DateOnly
            DateOnly dataInicio = DateOnly.FromDateTime(start);
            DateOnly dataFim = DateOnly.FromDateTime(end);

            var response = new DashboardDto();

            // buscar check-ins do usuário no período
            var checkIns = await _context.CheckIns
                .Include(c => c.Habit)
                .Where(c => c.Habit.UserId == userId 
                         && c.Date >= dataInicio 
                         && c.Date <= dataFim)
                .ToListAsync();

            // Monta o Gráfico de Consistência 
            response.ConsistencyChart = checkIns
                .GroupBy(c => c.Date)
                .Select(g => new ChartPointDto
                {
                    Date = g.Key.ToString("dd/MM"),
                    Count = g.Count()
                })
                .OrderBy(c => c.Date)
                .ToList();

          
            // Filtramos apenas hábitos que NÃO estão pausados 
            var habitosAtivos = await _context.Habits
                .Where(h => h.UserId == userId && !h.IsPaused) 
                .ToListAsync();

            var stats = new List<HabitStatDto>();
            
            // Calcula quantos dias existem no intervalo selecionado
            int diasTotais = dataFim.DayNumber - dataInicio.DayNumber + 1;

            foreach (var habit in habitosAtivos)
            {
                // Conta quantos check-ins existem para este hábito específico
                int feitos = checkIns.Count(c => c.HabitId == habit.Id);
                
                // Regra de 3 para achar a porcentagem
                double taxa = diasTotais > 0 ? ((double)feitos / diasTotais) * 100 : 0;

                stats.Add(new HabitStatDto
                {
                    Title = habit.Title,
                    TotalCheckIns = feitos,
                    CompletionRate = Math.Round(taxa, 1)
                });
            }

            if (stats.Any())
            {
                // Hábito com maior taxa
                response.MostCompletedHabit = stats.OrderByDescending(x => x.CompletionRate).FirstOrDefault();
                
                //Hábito com menor taxa, tirando os pausados 
                response.LeastCompletedHabit = stats.OrderBy(x => x.CompletionRate).FirstOrDefault();
            }

            return response;
        }
    }
}