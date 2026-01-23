using System.ComponentModel.DataAnnotations;

namespace MindHub.Domain.Models
{
    public enum FrequencyType
    {
        Daily = 0,
        SpecificDays = 1,
        TimesPerWeek = 2
    }

    public class Habit
    {
        public int Id { get; set; }

        public int UserId { get; set; }

        [Required]
        [MaxLength(100)]
        public string Title { get; set; } = string.Empty;

        [MaxLength(500)]
        public string? Description { get; set; }

        public FrequencyType Frequency { get; set; }

        public List<DayOfWeek>? SpecificDays { get; set; }

        public int? TargetCountPerWeek { get; set; }

        public string? ColorHex { get; set; }
        public string? IconName { get; set; }
        public TimeSpan? ReminderTime { get; set; }

        public bool IsPaused { get; set; } = false; 

        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    }
}