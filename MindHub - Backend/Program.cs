using MindHub.Domain.Models;
using MindHub.Infrastructure.Data;
using MindHub.Services;
using MindHub.Application.DTOs;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlite("Data Source=app.db"));

// Serviços de Regra de Negócio e Integrações
builder.Services.AddScoped<GamificationService>();
builder.Services.AddScoped<DashboardService>();
builder.Services.AddScoped<CalendarService>(); 
builder.Services.AddScoped<SecurityService>();
builder.Services.AddScoped<EmailService>();
builder.Services.AddScoped<AuthService>();

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();
app.MapControllers();

// Cria um escopo temporário para rodar o Dashboard no terminal
// (Necessário porque services "Scoped" não existem fora de uma requisição HTTP)
using (var scope = app.Services.CreateScope())
{
    var services = scope.ServiceProvider;
    try
    {
        var context = services.GetRequiredService<AppDbContext>();
        var dashboardService = services.GetRequiredService<DashboardService>();

        // Pega o primeiro usuário do banco apenas para exibir no painel
        var user = context.UserProfiles.FirstOrDefault();
        var userId = user?.Id ?? 0;

        Console.ForegroundColor = ConsoleColor.Cyan;
        Console.WriteLine("\n========================================================");
        Console.WriteLine("          🚀 MINDHUB - SISTEMA INICIADO      ");
        Console.WriteLine("========================================================");
        Console.ResetColor();

        Console.WriteLine("\n[1] STATUS DOS MÓDULOS:");
        PrintStatus("Banco de Dados", true);
        PrintStatus("CRUD de Hábitos", true);
        PrintStatus("Gamificação", true);
        PrintStatus("Autenticação", true);
        PrintStatus("Calendário", true);

        if (user != null)
        {
            var totalHabitos = context.Habits.Count(h => h.UserId == userId);
            var habitosPausados = context.Habits.Count(h => h.UserId == userId && h.IsPaused);
            
            // Lógica simples de nível: A cada 100 XP sobe 1 nível
            var nivelCalculado = (user.CurrentPoints / 100) + 1;

            Console.WriteLine($"\n[2] USUÁRIO LOGADO: {user.Name} ({user.Email})");
            Console.WriteLine($"   • Nível: {nivelCalculado} | XP: {user.CurrentPoints}");
            Console.WriteLine($"   • Hábitos Ativos: {totalHabitos - habitosPausados}");

            Console.WriteLine("\n[3] CONSISTÊNCIA (Últimos 7 dias):");
            var end = DateTime.Now;
            var start = end.AddDays(-6);
            
            var dashData = await dashboardService.GetDashboardAsync(userId, start, end);

            foreach (var point in dashData.ConsistencyChart)
            {
                // Cria uma barra visual baseada na quantidade (Ex: "■■■■")
                string bar = point.Count > 0 ? new string('■', point.Count * 2) : "."; 
                
                Console.Write($"   {point.Date}: ");
                Console.ForegroundColor = point.Count > 0 ? ConsoleColor.Green : ConsoleColor.Gray;
                Console.WriteLine($"{bar} ({point.Count})");
                Console.ResetColor();
            }

            Console.WriteLine("\n[4] DESTAQUES:");
            if (dashData.MostCompletedHabit != null)
            {
                Console.ForegroundColor = ConsoleColor.Yellow;
                Console.WriteLine($"   🏆 Melhor Hábito: {dashData.MostCompletedHabit.Title}");
                Console.ResetColor();
            }
            else
            {
                Console.WriteLine("   (Sem dados suficientes para análise)");
            }
        }
        else
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("\n[AVISO] Nenhum usuário encontrado.");
            Console.WriteLine("Use o Swagger para criar sua conta.");
            Console.ResetColor();
        }
        Console.WriteLine("\n========================================================\n");
    }
    catch (Exception ex)
    {
        Console.WriteLine($"Erro no dashboard: {ex.Message}");
    }
}

// Função auxiliar para imprimir status com cor condicional
void PrintStatus(string nome, bool feito)
{
    Console.Write($"   [{ (feito ? "OK" : "  ") }] {nome}");
    if (feito) 
    {
        Console.ForegroundColor = ConsoleColor.Green;
        Console.WriteLine(" ✔");
    }
    else 
    {
        Console.ForegroundColor = ConsoleColor.Red;
        Console.WriteLine(" ✘");
    }
    Console.ResetColor();
}

app.Run();