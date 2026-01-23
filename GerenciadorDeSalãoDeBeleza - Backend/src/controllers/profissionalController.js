const profissionalService = require('../services/profissionalService');

const profissionalController = {
    async listarProfissionais(req, res){
        try{
            const profissionais = await profissionalService.listarProfissionais();
            return res.status(200).json(profissionais);
        } catch (err) {
            console.error("Erro ao listar profissionais: ", err);
            return res.status(500).json({ erro: "Erro interno ao listar profissionais!" });
        }
    }
};

module.exports = profissionalController;