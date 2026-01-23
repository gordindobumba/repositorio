using Microsoft.AspNetCore.Mvc;
using MindHub.Services;
using MindHub.Application.DTOs;

namespace MindHub.API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class AuthController : ControllerBase
    {
        private readonly AuthService _authService;

        public AuthController(AuthService authService)
        {
            _authService = authService;
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegisterDto dto)
        {
            var result = await _authService.RegisterAsync(dto);
            
            if (result == "Sucesso")
                return Ok(new { message = "Usuário cadastrado com sucesso!" });

            return BadRequest(new { message = result });
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginDto dto)
        {
            var (token, error) = await _authService.LoginAsync(dto);

            if (token == null)
                return Unauthorized(new { message = error });

            return Ok(new { token, message = "Login realizado com sucesso!" });
        }

        [HttpPost("logout")]
        public async Task<IActionResult> Logout([FromQuery] string token)
        {
            var success = await _authService.LogoutAsync(token);
            
            if (success)
                return Ok(new { message = "Logout realizado com sucesso." });
            
            return BadRequest(new { message = "Token inválido." });
        }

        [HttpPost("forgot-password")]
        public async Task<IActionResult> ForgotPassword([FromBody] ForgotPasswordDto dto)
        {
            var message = await _authService.ForgotPasswordAsync(dto.Email);
            return Ok(new { message });
        }

        [HttpPost("reset-password")]
        public async Task<IActionResult> ResetPassword([FromBody] ResetPasswordDto dto)
        {
            var result = await _authService.ResetPasswordAsync(dto);

            if (result == "Sucesso")
                return Ok(new { message = "Senha redefinida com sucesso!" });

            return BadRequest(new { message = result });
        }
    }
}