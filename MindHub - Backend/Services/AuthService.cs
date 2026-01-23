using Microsoft.EntityFrameworkCore;
using MindHub.Infrastructure.Data;
using MindHub.Domain.Models;
using MindHub.Application.DTOs;

namespace MindHub.Services
{
    public class AuthService
    {
        private readonly AppDbContext _context;
        private readonly SecurityService _security;
        private readonly EmailService _emailService;

        public AuthService(AppDbContext context, SecurityService security, EmailService emailService)
        {
            _context = context;
            _security = security;
            _emailService = emailService;
        }

        // REGISTRAR 
        public async Task<string> RegisterAsync(RegisterDto dto)
        {
            // Validações
            if (await _context.UserProfiles.AnyAsync(u => u.Email == dto.Email))
                return "E-mail já cadastrado.";

            if (!_security.IsStrongPassword(dto.Password))
                return "Senha fraca. Use pelo menos 8 caracteres, números, maiúsculas e símbolos.";

            // Cria o usuário
            var user = new UserProfile
            {
                Name = dto.Name,
                Email = dto.Email,
                PasswordHash = _security.HashPassword(dto.Password), // Criptografa
                CurrentPoints = 0
            };

            _context.UserProfiles.Add(user);
            await _context.SaveChangesAsync();

            return "Sucesso"; // Código de sucesso
        }

        //  LOGIN
        public async Task<(string? token, string error)> LoginAsync(LoginDto dto)
        {
            var user = await _context.UserProfiles
                .FirstOrDefaultAsync(u => u.Email == dto.Email);

            if (user == null) return (null, "Usuário ou senha incorretos.");

            // Verifica a senha
            bool isPasswordValid = _security.VerifyPassword(dto.Password, user.PasswordHash);
            if (!isPasswordValid) return (null, "Usuário ou senha incorretos.");

            // Gera o Token (Sessão)
            var token = Guid.NewGuid().ToString(); // Token simples (UUID)

            var session = new UserSession
            {
                UserId = user.Id,
                Token = token,
                CreatedAt = DateTime.UtcNow
            };

            _context.UserSessions.Add(session);
            await _context.SaveChangesAsync();

            return (token, string.Empty);
        }

        //  LOGOUT 
        public async Task<bool> LogoutAsync(string token)
        {
            var session = await _context.UserSessions.FirstOrDefaultAsync(s => s.Token == token);
            if (session == null) return false;

            _context.UserSessions.Remove(session);
            await _context.SaveChangesAsync();
            return true;
        }

        // RECUPERAR SENHA 
        public async Task<string> ForgotPasswordAsync(string email)
        {
            var user = await _context.UserProfiles.FirstOrDefaultAsync(u => u.Email == email);
            
            // Se não achar o email, finge que enviou para não revelar quem tem conta
            if (user == null) return "Se o e-mail existir, o código foi enviado.";

            // Gera código de 6 dígitos
            string code = new Random().Next(100000, 999999).ToString();

            var recovery = new PasswordRecoveryCode
            {
                Email = email,
                Code = code,
                Expiration = DateTime.UtcNow.AddMinutes(15) // Vale por 15 min
            };

            _context.PasswordRecoveryCodes.Add(recovery);
            await _context.SaveChangesAsync();

            // Envia o E-mail
            string corpoEmail = $"<h3>Recuperação de Senha MindHub</h3><p>Seu código é: <b>{code}</b></p>";
            await _emailService.SendEmailAsync(email, "Código de Recuperação", corpoEmail);

            return "Se o e-mail existir, o código foi enviado.";
        }

        // RESETAR SENHA (USAR O CÓDIGO)
        public async Task<string> ResetPasswordAsync(ResetPasswordDto dto)
        {
            var recovery = await _context.PasswordRecoveryCodes
                .FirstOrDefaultAsync(r => r.Code == dto.Code && !r.IsUsed);

            if (recovery == null) return "Código inválido.";
            if (recovery.Expiration < DateTime.UtcNow) return "Código expirado.";

            var user = await _context.UserProfiles.FirstOrDefaultAsync(u => u.Email == recovery.Email);
            if (user == null) return "Erro interno: usuário não encontrado.";

            if (!_security.IsStrongPassword(dto.NewPassword))
                return "Senha fraca. Use letras, números e símbolos.";

            // Atualiza a senha
            user.PasswordHash = _security.HashPassword(dto.NewPassword);
            
            // Marca código como usado
            recovery.IsUsed = true;
            
            await _context.SaveChangesAsync();
            return "Sucesso";
        }
        
        // Pega o ID do usuário através do Token que vem no cabeçalho
        public async Task<int?> GetUserIdByTokenAsync(string token)
        {
            var session = await _context.UserSessions
                .Include(s => s.User)
                .FirstOrDefaultAsync(s => s.Token == token);

            return session?.UserId;
        }
    }
}