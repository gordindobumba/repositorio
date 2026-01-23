namespace MindHub.DTOs
{
    public class DashboardDto
    {
        public HabitStatDto? MostCompletedHabit { get; set; }
        public HabitStatDto? LeastCompletedHabit { get; set; }
        public List<ChartPointDto> ConsistencyChart { get; set; } = new();
    }

    public class HabitStatDto
    {
        public string Title { get; set; } = string.Empty;
        public int TotalCheckIns { get; set; }
        public double CompletionRate { get; set; }
    }

    public class ChartPointDto
    {
        public string Date { get; set; } = string.Empty;
        public int Count { get; set; }
    }
}