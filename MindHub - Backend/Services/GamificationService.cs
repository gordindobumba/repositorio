using Microsoft.EntityFrameworkCore;
using MindHub.Domain.Models;
using MindHub.Infrastructure.Data;

namespace MindHub.Services
{
    public class GamificationService
    {
        private readonly AppDbContext _context;

        public GamificationService(AppDbContext context)
        {
            _context = context;
        }

        // Chamado quando o usuário MARCA o hábito (Ganha pontos)
        public async Task ProcessCheckInAsync(int userProfileId)
        {
            var user = await _context.UserProfiles.FindAsync(userProfileId);
            if (user == null) return;

            // 1. Dá os pontos
            user.CurrentPoints += 10;
            
            // 2. Verifica se ganhou emblemas
            await CheckWeeklyBadgeAsync(userProfileId);
            await CheckMonthlyTrophyAsync(userProfileId);

            // 3. Salva tudo (Pontos e Emblemas)
            await _context.SaveChangesAsync();
        }

        // NOVO: Chamado quando o usuário DESMARCA (Perde pontos)
        public async Task RemovePointsAsync(int userProfileId)
        {
            var user = await _context.UserProfiles.FindAsync(userProfileId);
            if (user != null)
            {
                // Remove 10 pontos
                user.CurrentPoints -= 10;

                // Segurança: Não deixa ficar negativo
                if (user.CurrentPoints < 0) user.CurrentPoints = 0;
                
                await _context.SaveChangesAsync();
            }
        }

        private async Task CheckWeeklyBadgeAsync(int userId)
        {
            bool alreadyHas = await _context.UserAchievements
                .AnyAsync(ua => ua.UserProfileId == userId && ua.AchievementId == 1);
            if (alreadyHas) return;

            var sevenDaysAgo = DateOnly.FromDateTime(DateTime.UtcNow.AddDays(-7));
            var today = DateOnly.FromDateTime(DateTime.UtcNow);

            int checkInsCount = await _context.CheckIns
                .CountAsync(c => c.Date >= sevenDaysAgo && c.Date <= today);

            int targetCheckIns = 20; // Meta simplificada para o MVP

            if (checkInsCount >= targetCheckIns)
            {
                _context.UserAchievements.Add(new UserAchievement
                {
                    UserProfileId = userId,
                    AchievementId = 1,
                    UnlockedAt = DateTime.UtcNow
                });
            }
        }

        private async Task CheckMonthlyTrophyAsync(int userId)
        {
            bool alreadyHas = await _context.UserAchievements
                .AnyAsync(ua => ua.UserProfileId == userId && ua.AchievementId == 2);
            if (alreadyHas) return;

            var habits = await _context.Habits.ToListAsync();
            var today = DateOnly.FromDateTime(DateTime.UtcNow);

            foreach (var habit in habits)
            {
                bool streakBroken = false;
                for (int i = 0; i < 30; i++)
                {
                    var dateToCheck = today.AddDays(-i);
                    bool hasCheckIn = await _context.CheckIns
                        .AnyAsync(c => c.HabitId == habit.Id && c.Date == dateToCheck);
                    
                    if (!hasCheckIn)
                    {
                        streakBroken = true;
                        break;
                    }
                }

                if (!streakBroken)
                {
                    _context.UserAchievements.Add(new UserAchievement
                    {
                        UserProfileId = userId,
                        AchievementId = 2,
                        UnlockedAt = DateTime.UtcNow
                    });
                    break;
                }
            }
        }
    }
}