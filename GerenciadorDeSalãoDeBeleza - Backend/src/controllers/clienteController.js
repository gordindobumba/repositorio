const clienteService = require('../services/clienteService');

const clienteController = {
    async listarClientes(req, res){
        try{
            const clientes = await clienteService.listarClientes();
            return res.status(200).json(clientes);
        } catch (err) {
            console.error("Erro ao listar clientes: ", err);
            return res.status(500).json({ erro: "Erro interno ao listar clientes!" });
        }
    }
};

module.exports = clienteController;