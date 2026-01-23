using System.ComponentModel.DataAnnotations;

namespace MindHub.Domain.Models
{
    public class UserProfile
    {
        public int Id { get; set; }

        [Required]
        public string Name { get; set; } = string.Empty;

        [Required]
        [EmailAddress]
        public string Email { get; set; } = string.Empty; 

        [Required]
        public string PasswordHash { get; set; } = string.Empty; // Senha criptografada
        // --------------------------------

        public int CurrentPoints { get; set; } = 0; 
    }
}