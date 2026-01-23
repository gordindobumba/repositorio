using Microsoft.EntityFrameworkCore;
using MindHub.Infrastructure.Data;
using MindHub.Domain.Models;
using MindHub.Application.DTOs;

namespace MindHub.Services
{
    public class CalendarService
    {
        private readonly AppDbContext _context;

        public CalendarService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<CalendarMonthDto> GenerateCalendarAsync(int userId, int year, int month)
        {
            // 1. Busca todos os hábitos do usuário
            var habits = await _context.Habits
                .Where(h => h.UserId == userId && !h.IsPaused)
                .ToListAsync();

            // 2. Busca todos os check-ins feitos naquele mês
            var checkIns = await _context.CheckIns
                .Include(c => c.Habit)
                .Where(c => c.Habit!.UserId == userId 
                            && c.CheckInDate.Year == year 
                            && c.CheckInDate.Month == month)
                .ToListAsync();

            var calendar = new CalendarMonthDto { Year = year, Month = month };
            int daysInMonth = DateTime.DaysInMonth(year, month);

            // 3. Loop: Passa por cada dia do mês (do dia 1 ao 30/31)
            for (int day = 1; day <= daysInMonth; day++)
            {
                DateTime currentDate = new DateTime(year, month, day);
                
                // Descobre quais hábitos deveriam ser feitos neste dia
                var activeHabits = habits.Where(h => ShouldDoHabit(h, currentDate)).ToList();
                
                int total = activeHabits.Count;
                int completed = 0;

                // Verifica quantos foram completados
                foreach (var habit in activeHabits)
                {
                    bool isDone = checkIns.Any(c => c.HabitId == habit.Id && c.CheckInDate.Day == day);
                    if (isDone) completed++;
                }

                // Define a cor (Lógica do outro estagiário)
                string color = GetColor(total, completed, currentDate);

                calendar.Days.Add(new CalendarDayDto
                {
                    Day = day,
                    Color = color,
                    TotalHabits = total,
                    CompletedHabits = completed,
                    CompletionRate = total == 0 ? 0 : (double)completed / total * 100,
                    HabitNames = activeHabits.Select(h => h.Title).ToList()
                });
            }

            return calendar;
        }

        // --- LÓGICA DE REGRAS ---

        // Verifica se o hábito deve ser feito naquela data
        private bool ShouldDoHabit(Habit habit, DateTime date)
        {


            
            if (habit.Frequency == "Diario") return true;

            if (habit.Frequency == "Semanal")
            {
                // Verifica se o dia da semana bate (Ex: "Monday,Wednesday")
                // Se o seu banco salva como string, precisamos converter
                string weekDay = date.DayOfWeek.ToString(); 
                // Assumindo que você salva os dias na string Frequency ou outro campo
                // Se for simples, retornamos true para testes.
                return true; 
            }

            return true; // Por padrão, assume que deve fazer
        }

        // Lógica de Cores 
        private string GetColor(int total, int completed, DateTime date)
        {
            if (total == 0 || date.Date > DateTime.Now.Date) return "#515151"; // Cinza 
            double percentage = (double)completed / total * 100.0;

            if (percentage == 100.0) return "#008000"; // Verde
            if (percentage > 0.0) return "#FFDE21";    // Amarelo 
            
            return "#FF0000"; // Vermelho 
        }
    }
}