const agendamentoService = require('../services/agendamentoService');

const agendamentoController = {
    async listarAgendamentos(req, res){
        try{
            const agendamentos = await agendamentoService.listarAgendamentos();
            return res.status(200).json(agendamentos);
        }catch (err){
            console.error('Erro ao listar agendamentos:', err);
            return res.status(500).json({ erro: 'Erro interno ao listar agendamentos' });
        }
    }
}

module.exports = agendamentoController;