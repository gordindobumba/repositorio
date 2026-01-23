# 🧠 MindHub API

API do sistema de gestão de hábitos (Backend).

## 🚀 Tecnologias
- .NET 9.0
- Entity Framework Core
- SQLite

## 📦 Como Rodar

1. **Pré-requisitos**: Tenha o [.NET SDK 9.0](https://dotnet.microsoft.com/download) instalado.

2. **Restaurar pacotes**:
   ```bash
   dotnet restore

3. **Criar o Banco de Dados Local**: O arquivo app.db não é versionado (está no .gitignore). Para criar o seu localmente, rode: 
    dotnet ef database update

4. **Rodar a API**: 
    dotnet run

Acesse o Swagger em: http://localhost:5000/swagger (verificar a porta exata no terminal).