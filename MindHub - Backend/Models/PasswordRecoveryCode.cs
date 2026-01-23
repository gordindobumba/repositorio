using System.ComponentModel.DataAnnotations;

namespace MindHub.Domain.Models
{
    public class PasswordRecoveryCode
    {
        public int Id { get; set; }
        public string Email { get; set; } = string.Empty;
        public string Code { get; set; } = string.Empty;
        public DateTime Expiration { get; set; } // Validade do código
        public bool IsUsed { get; set; } = false; // Já foi usado?
    }
}