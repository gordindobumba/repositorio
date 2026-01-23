using Microsoft.AspNetCore.Mvc;
using MindHub.Services;

namespace MindHub.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class DashboardController : ControllerBase
    {
        private readonly DashboardService _service;

        public DashboardController(DashboardService service)
        {
            _service = service;
        }

        [HttpGet]
        public async Task<IActionResult> GetDashboard(
            [FromQuery] int userId, 
            [FromQuery] DateTime? start, 
            [FromQuery] DateTime? end)
        {
            if (userId <= 0) return BadRequest("UserId inválido");

            // se nao passar data, pega os ultimos 30 dias 
            var dataInicio = start ?? DateTime.Now.AddDays(-30);
            var dataFim = end ?? DateTime.Now;

            try
            {
                var resultado = await _service.GetDashboardAsync(userId, dataInicio, dataFim);
                return Ok(resultado);
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Erro interno: {ex.Message}");
            }
        }
    }
}