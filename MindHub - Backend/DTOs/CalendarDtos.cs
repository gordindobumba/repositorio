namespace MindHub.Application.DTOs
{
    // O que o Frontend vai receber: O Mês inteiro
    public class CalendarMonthDto
    {
        public int Year { get; set; }
        public int Month { get; set; }
        public List<CalendarDayDto> Days { get; set; } = new();
    }

    // O detalhe de cada dia (cor, quantos completou, etc)
    public class CalendarDayDto
    {
        public int Day { get; set; }
        public string Color { get; set; } = "#515151"; // Cinza 
        public int TotalHabits { get; set; }
        public int CompletedHabits { get; set; }
        public double CompletionRate { get; set; }
        public List<string> HabitNames { get; set; } = new();
    }
}