namespace MindHub.Domain.Models
{
    public class UserAchievement
    {
        public int Id { get; set; }
        
        public int UserProfileId { get; set; }
        public UserProfile? UserProfile { get; set; }

        public int AchievementId { get; set; }
        public Achievement? Achievement { get; set; }

        public DateTime UnlockedAt { get; set; } = DateTime.UtcNow;
    }
}