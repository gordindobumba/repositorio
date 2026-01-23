const db = require('../../db');

const profissionalService = {
    async listarProfissionais(){
        const sql = "SELECT * FROM Profissional";
        const [linhas] = await db.query(sql);
        return linhas;
    }
};

module.exports = profissionalService;