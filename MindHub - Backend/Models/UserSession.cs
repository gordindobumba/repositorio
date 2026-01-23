using System.ComponentModel.DataAnnotations;

namespace MindHub.Domain.Models
{
    public class UserSession
    {
        public int Id { get; set; }
        
        public int UserId { get; set; } // Quem é o usuário?
        public UserProfile? User { get; set; } // Link para a tabela de usuários

        public string Token { get; set; } = string.Empty;
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    }
}