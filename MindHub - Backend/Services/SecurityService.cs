using Isopoh.Cryptography.Argon2;
using System.Text.RegularExpressions;

namespace MindHub.Services
{
    public class SecurityService
    {
        // Transforma a senha em um código seguro criptografado
        public string HashPassword(string password)
        {
            return Argon2.Hash(password);
        }

        // Compara a senha digitada com o hash salvo no banco
        public bool VerifyPassword(string password, string hash)
        {
            try 
            {
                return Argon2.Verify(hash, password);
            }
            catch 
            { 
                // Se o hash estiver corrompido ou inválido
                return false; 
            }
        }

        // Regra de Negócio (RF01): Valida força da senha
        // Mínimo 8 caracteres, 1 número, 1 letra maiúscula, 1 símbolo
        public bool IsStrongPassword(string password)
        {
            if (string.IsNullOrWhiteSpace(password)) return false;
            if (password.Length < 8) return false;
            if (!Regex.IsMatch(password, "[0-9]")) return false; 
            if (!Regex.IsMatch(password, "[A-Z]")) return false; 
            if (!Regex.IsMatch(password, "[^a-zA-Z0-9]")) return false; 
            
            return true;
        }
    }
}