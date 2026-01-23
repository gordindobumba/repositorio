using System.ComponentModel.DataAnnotations;

namespace MindHub.Domain.Models
{
    public class HabitCheckIn
    {
        public int Id { get; set; }

        public int HabitId { get; set; }
        public Habit? Habit { get; set; } // Navegação para saber de qual hábito é

        // DateOnly é perfeito para guardar "2025-10-12" sem se preocupar com horas
        public DateOnly Date { get; set; } 

        public DateTime CompletedAt { get; set; } = DateTime.UtcNow;
    }
}