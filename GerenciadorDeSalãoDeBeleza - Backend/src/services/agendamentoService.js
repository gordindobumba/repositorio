const db = require('../../db');
const AgendaRepository = require('../repository/agendaRepository');

const agendamentoService = {

    async listarAgendamentos() {
        return AgendaRepository.AcharAgenda(1);
    }
};

module.exports = agendamentoService;