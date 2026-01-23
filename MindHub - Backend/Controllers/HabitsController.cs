using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore; // Importante para usar o Banco
using MindHub.Domain.Models;
using MindHub.Application.DTOs;
using MindHub.Infrastructure.Data;

namespace MindHub.API.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class HabitsController : ControllerBase
    {
        private readonly AppDbContext _context;

        // Injeção de Dependência: O sistema entrega o banco pronto para uso aqui
        public HabitsController(AppDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<IActionResult> GetAll()
        {
            // Busca todos os hábitos no banco de dados
            var habits = await _context.Habits.ToListAsync();
            return Ok(habits);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            var habit = await _context.Habits.FindAsync(id);
            if (habit == null) return NotFound();
            return Ok(habit);
        }

        [HttpPost]
        public async Task<IActionResult> Create([FromBody] CreateHabitDto dto)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            // Validações de Regra de Negócio
            if (dto.Frequency == FrequencyType.SpecificDays && (dto.SpecificDays == null || !dto.SpecificDays.Any()))
                return BadRequest("Selecione pelo menos um dia da semana.");

            if (dto.Frequency == FrequencyType.TimesPerWeek && (dto.TargetCountPerWeek == null || dto.TargetCountPerWeek <= 0))
                return BadRequest("Defina quantas vezes por semana.");

            var habit = new Habit
            {
                // O ID é gerado automaticamente pelo banco agora
                Title = dto.Title,
                Description = dto.Description,
                Frequency = dto.Frequency,
                SpecificDays = dto.Frequency == FrequencyType.SpecificDays ? dto.SpecificDays : null,
                TargetCountPerWeek = dto.Frequency == FrequencyType.TimesPerWeek ? dto.TargetCountPerWeek : null,
                ColorHex = dto.ColorHex,
                IconName = dto.IconName,
                ReminderTime = dto.ReminderTime,
                CreatedAt = DateTime.UtcNow
            };

            _context.Habits.Add(habit);
            await _context.SaveChangesAsync(); // Salva no arquivo app.db

            return CreatedAtAction(nameof(GetById), new { id = habit.Id }, habit);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Update(int id, [FromBody] UpdateHabitDto dto)
        {
            var existingHabit = await _context.Habits.FindAsync(id);
            if (existingHabit == null) return NotFound();

            existingHabit.Title = dto.Title;
            existingHabit.Description = dto.Description;
            existingHabit.Frequency = dto.Frequency;
            existingHabit.SpecificDays = dto.Frequency == FrequencyType.SpecificDays ? dto.SpecificDays : null;
            existingHabit.TargetCountPerWeek = dto.Frequency == FrequencyType.TimesPerWeek ? dto.TargetCountPerWeek : null;
            existingHabit.ColorHex = dto.ColorHex;
            existingHabit.IconName = dto.IconName;
            existingHabit.ReminderTime = dto.ReminderTime;

            // O Entity Framework detecta que mudou e gera o SQL de update
            await _context.SaveChangesAsync();

            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            var habit = await _context.Habits.FindAsync(id);
            if (habit == null) return NotFound();

            _context.Habits.Remove(habit);
            await _context.SaveChangesAsync();

            return NoContent();
        }
    }
}