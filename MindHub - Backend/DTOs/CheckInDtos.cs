namespace MindHub.Application.DTOs
{
    // O que o Frontend vai receber ao pedir a lista do dia
    public class DailyHabitDto
    {
        public int HabitId { get; set; }
        public string Title { get; set; } = string.Empty;
        public bool IsCompleted { get; set; } // O checkbox está marcado?
    }

    // O que o Frontend envia para marcar/desmarcar
    public class ToggleCheckInDto
    {
        public int HabitId { get; set; }
        public DateOnly Date { get; set; }
    }
}