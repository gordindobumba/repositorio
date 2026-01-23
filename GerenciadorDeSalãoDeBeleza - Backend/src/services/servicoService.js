//forma padrao valores ja validados pelo controller

const db = require("../../db");

const servicoService = {
  async criarServico(servicoData) {
    const { nome, descrição, valor_base, tempo_estimado } = servicoData;

    const sql = `
      INSERT INTO \`Serviço\` (nome, descrição, valor_base, tempo_estimado)
      VALUES (?, ?, ?, ?)
    `;

    const [result] = await db.query(sql, [nome, descrição, valor_base, tempo_estimado]);
    return result;
  },

  async removerServico(id) {
    const [result] = await db.query('SELECT * FROM Serviço WHERE id = ?', [id]);

    if(result.length === 0){
      return -1;
    }

    const [resultado] = await db.query('DELETE FROM Serviço WHERE id = ?', [id]);

    return resultado.affectedRows > 0;
  },

  async atualizarServico(servicoData) {
    const { id, nome, descrição, valor_base, tempo_estimado } = servicoData;
    const [resultado] = await db.query('SELECT * FROM Serviço WHERE id = ?', [id]);

    if (resultado.length === 0) {
      return -1;
    }

    const [result] = await db.query('UPDATE Serviço SET nome = ?, descrição = ?, valor_base = ?, tempo_estimado = ? WHERE id = ?',
      [nome, descrição, valor_base, tempo_estimado, id]);
  },
    
  async listarServicos() {
    const sql = "SELECT * FROM `Serviço`";
    const [linhas] = await db.query(sql);
    return linhas;
  },

  async obterServicoPorId(id) {
  const sql = "SELECT * FROM `Serviço` WHERE id = ?";
    const [linhas] = await db.query(sql, [id]);

    if (linhas.length === 0) {
      throw new Error("SERVICO_NAO_ENCONTRADO");
    }

    return linhas[0];
  }
};


module.exports = servicoService;
