const db = require('../../db');

const clienteService = {
    async listarClientes(){
        const sql = "SELECT * FROM Cliente";
        const [linhas] = await db.query(sql);
        return linhas;
    }
};

module.exports = clienteService;