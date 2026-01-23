using Microsoft.AspNetCore.Mvc;
using MindHub.Services;

namespace MindHub.API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class CalendarController : ControllerBase
    {
        private readonly CalendarService _calendarService;
        private readonly AuthService _authService;

        public CalendarController(CalendarService calendarService, AuthService authService)
        {
            _calendarService = calendarService;
            _authService = authService;
        }

        [HttpGet("{year}/{month}")]
        public async Task<IActionResult> GetMonthlyCalendar(int year, int month)
        {
            // Pega o Token do cabeçalho para saber quem é o usuário
            string token = Request.Headers["Authorization"].ToString().Replace("Bearer ", "");
            var userId = await _authService.GetUserIdByTokenAsync(token);

            if (userId == null)
                return Unauthorized(new { message = "Você precisa estar logado." });

            var calendar = await _calendarService.GenerateCalendarAsync(userId.Value, year, month);
            return Ok(calendar);
        }
    }
}