using MailKit.Net.Smtp;
using MimeKit;

namespace MindHub.Services
{
    public class EmailService
    {
        public async Task SendEmailAsync(string toEmail, string subject, string body)
        {
            var message = new MimeMessage();
            
            // CONFIGURAÇÃO: Quem está enviando?
            message.From.Add(new MailboxAddress("MindHub System", "no-reply@mindhub.com"));
            
            // Quem vai receber?
            message.To.Add(new MailboxAddress("", toEmail));
            
            message.Subject = subject;

            // O corpo do e-mail (aceita HTML)
            message.Body = new TextPart("html")
            {
                Text = body
            };

            using (var client = new SmtpClient())
            {
                // ATENÇÃO: Aqui conectamos no servidor de e-mail.
                // Para testes, isso não envia de verdade se não tiver um servidor real.
                // Exemplo Gmail: client.Connect("smtp.gmail.com", 587, false);
                
                try 
                {
                    // Por enquanto, apenas simulamos o envio para não dar erro
                    // No futuro, você configura seu servidor SMTP aqui.
                    Console.WriteLine($"[SIMULAÇÃO DE EMAIL] Para: {toEmail} | Assunto: {subject}");
                    Console.WriteLine($"Conteúdo: {body}");
                    
                    // em caso de utilização real, devemos descomentar as linhas abaixo e configurar o servidor
                    // await client.ConnectAsync("smtp.tal-servidor.com", 587, false);
                    // await client.AuthenticateAsync("seu-email@dominio.com", "sua-senha");
                    // await client.SendAsync(message);
                    // await client.DisconnectAsync(true);
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Erro ao enviar e-mail: {ex.Message}");
                }
            }
        }
    }
}