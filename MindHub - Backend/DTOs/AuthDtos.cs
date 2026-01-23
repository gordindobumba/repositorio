using System.ComponentModel.DataAnnotations;

namespace MindHub.Application.DTOs
{
    // Dados para Cadastrar (RF01)
    public class RegisterDto
    {
        [Required]
        public string Name { get; set; } = string.Empty;

        [Required]
        [EmailAddress]
        public string Email { get; set; } = string.Empty;

        [Required]
        [MinLength(6, ErrorMessage = "A senha deve ter pelo menos 6 caracteres.")]
        public string Password { get; set; } = string.Empty;
    }

    // Dados para Login (RF02)
    public class LoginDto
    {
        [Required]
        public string Email { get; set; } = string.Empty;

        [Required]
        public string Password { get; set; } = string.Empty;
    }

    // Dados para Editar Perfil (RF08)
    public class UpdateProfileDto
    {
        public string? NewName { get; set; }
        public string? CurrentPassword { get; set; } // Necessário para trocar senha
        public string? NewPassword { get; set; }
    }

    // Dados para Esqueci Minha Senha (RF09)
    public class ForgotPasswordDto
    {
        [Required]
        [EmailAddress]
        public string Email { get; set; } = string.Empty;
    }

    // Dados para Resetar a Senha (RF10)
    public class ResetPasswordDto
    {
        [Required]
        public string Code { get; set; } = string.Empty; // O código que chegou no email

        [Required]
        [MinLength(6)]
        public string NewPassword { get; set; } = string.Empty;
    }
}