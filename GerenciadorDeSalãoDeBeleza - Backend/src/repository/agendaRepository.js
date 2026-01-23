const MOCK_DB = [
  { id: 1, data: '13/01/2026', hora_inicio: '17:30:00', hora_fim: "18:00:00", status: "agendado", cliente_id: "1", profissional_id: "2" }
];

class AgendaRepository {

  static async AcharAgenda(id) {
    const agenda = MOCK_DB.find(a => a.id === id);
    return agenda;
  }
}

module.exports = AgendaRepository;
