namespace MindHub.Domain.Models
{
    public class Achievement
    {
        public int Id { get; set; }
        public string Title { get; set; } = string.Empty; // Ex: "Semana Perfeita"
        public string Description { get; set; } = string.Empty;
        public string IconName { get; set; } = string.Empty; 
    }
}