using System.ComponentModel.DataAnnotations;
using MindHub.Domain.Models;

namespace MindHub.Application.DTOs
{
    public class CreateHabitDto
    {
        [Required(ErrorMessage = "O título é obrigatório.")]
        public string Title { get; set; } = string.Empty;

        public string? Description { get; set; }

        [Required]
        public FrequencyType Frequency { get; set; }

        public List<DayOfWeek>? SpecificDays { get; set; }

        public int? TargetCountPerWeek { get; set; }

        public string? ColorHex { get; set; }
        public string? IconName { get; set; }
        public TimeSpan? ReminderTime { get; set; }
    }

    public class UpdateHabitDto : CreateHabitDto
    {
        // Herda tudo de CreateHabitDto
    }
}