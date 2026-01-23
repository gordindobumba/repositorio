const servicoService = require("../services/servicoService");

const servicoController = {
  async criarServico(req, res) {
    const { nome, descrição, valor_base, tempo_estimado } = req.body;

    // validando campos obrigatorios, ta tudo?
    if (!nome || !descrição || !valor_base || !tempo_estimado) {
      return res.status(400).json({
        erro: "Todos os campos são obrigatórios: nome, descrição, valor_base, tempo_estimado"
      });
    }


    // validação de tipos e formatos básicos,
    //não tivemos documento de requisitos, então o tratamento de entrada
    //sera apenas para entradas IMPOSSÍVEIS, e um limite superior irrealista

    // Nome: string não vazia e nao pode ter mais que 50 caracteres
    if (typeof nome !== 'string' || nome.trim() === '') {
      return res.status(400).json({
        erro: "Nome deve ser uma string não vazia"
      });
    }

    if (nome.length > 50) {
      return res.status(400).json({
        erro: "Nome não pode ter mais de 50 caracteres"
      });
    }

    // Descrição: string não vazia, máximo 200 caracteres, parece um limite decente
    if (typeof descrição !== 'string' || descrição.trim() === '') {
      return res.status(400).json({
        erro: "Descrição deve ser uma string não vazia"
      });
    }

    if (descrição.length > 200) {
      return res.status(400).json({
        erro: "Descrição não pode ter mais de 200 caracteres"
      });
    }

    // Valor_base: número positivo (acima de 0) máximo 2 casas decimais tbm
    const valor = parseFloat(valor_base);
    if (isNaN(valor)) {
      return res.status(400).json({
        erro: "valor_base deve ser um número válido"
      });
    }

    if (valor <= 0) {
      return res.status(400).json({
        erro: "valor_base deve ser maior que zero"
      });
    }

    // Verificar se tem mais de 2 casas decimais!!
    if (!/^\d+(\.\d{1,2})?$/.test(valor_base.toString())) {
      return res.status(400).json({
        erro: "valor_base deve ter no máximo 2 casas decimais"
      });
    }

    //validação do tempo_estimado (o ter o formato perfeito)
    if (typeof tempo_estimado !== 'string') {
      return res.status(400).json({
        erro: "tempo_estimado deve ser uma string no formato HH:MM:SS"
      });
    }

    // Regex para validar formato HH:MM:SS (00:00:00 até 23:59:59)
    const tempoRegex = /^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$/;

    if (!tempoRegex.test(tempo_estimado)) {
      return res.status(400).json({
        erro: "tempo_estimado deve estar no formato HH:MM:SS (ex: 01:30:00)"
      });
    }

    // verificar se não é "00:00:00"
    if (tempo_estimado === "00:00:00") {
      return res.status(400).json({
        erro: "tempo_estimado não pode ser zero"
      });
    }

    // pra ver se é positivo, solução foi deixar tudo em segundos para fazer a comparação
    const [horas, minutos, segundos] = tempo_estimado.split(':').map(Number);
    const totalSegundos = horas * 3600 + minutos * 60 + segundos;

    if (totalSegundos <= 0) {
      return res.status(400).json({
        erro: "tempo_estimado deve ser maior que zero"
      });
    }

    try {
      const result = await servicoService.criarServico({
        nome: nome.trim(),
        descrição: descrição.trim(),
        valor_base: valor,
        tempo_estimado
      });

      res.status(201).json({
        mensagem: "Serviço criado com sucesso",
        id: result.insertId,
        nome: nome.trim(),
        descrição: descrição.trim(),
        valor_base: valor,
        tempo_estimado
      });

    } catch (err) {
      console.error("Erro ao criar serviço:", err);

      // Tratamento de erros específicos do MySQL
      if (err.code === 'ER_DUP_ENTRY') {
        return res.status(409).json({
          erro: "Já existe um serviço com este nome"
        });
      }

      if (err.code === 'ER_DATA_TOO_LONG') {
        return res.status(400).json({
          erro: "Dados muito longos para algum campo"
        });
      }

      if (err.code === 'ER_TRUNCATED_WRONG_VALUE') {
        return res.status(400).json({
          erro: "Formato de dados inválido"
        });
      }

      res.status(500).json({
        erro: "Erro interno ao criar serviço"
      });
    }
  },

  async removerServico(req, res) {
    const { id } = req.body;

    if (!id) {
      return res.status(400).json({ message: 'ID é obrigatório' });
    }
    try {
      const resultado = await servicoService.removerServico(id);

      if (resultado === -1) {
        return res.status(404).json({ message: 'Serviço não encontrado' });
      }

      return res.status(200).json({ message: 'Serviço removido com sucesso' });
    } catch (error) {
      return res.status(500).json({ error: error.message });
    }
  },

  async atualizarServico(req, res) {
    const { id, nome, descrição, valor_base, tempo_estimado } = req.body;


    if (!id) {
      return res.status(400).json({ message: 'ID é obrigatório para atualização' });
    }

    try {
      const resultado = await servicoService.atualizarServico({ id, nome, descrição, valor_base, tempo_estimado });

      if (resultado === -1) {
        return res.status(404).json({ message: 'Serviço não encontrado' });
      }

      return res.status(200).json({ message: 'Serviço atualizado com sucesso' });

    } catch (error) {
      return res.status(500).json({ error: error.message });
    }
  },
  
    async listarServicos(req, res) {
          try {
              const servicos = await servicoService.listarServicos();
              return res.status(200).json(servicos);
          } catch (err) {
              console.error("Erro ao listar serviços: ", err);
              return res.status(500).json({ erro: "Erro interno ao listar serviços!" });
          }
      },
    
      async obterServicoPorId(req, res) {
          const { id } = req.params;
          const idNumerico = Number(id);

          if (!Number.isInteger(idNumerico) || idNumerico <= 0) {
          return res.status(400).json({ erro: "ID inválido" });
          }

          try {
              const servico = await servicoService.obterServicoPorId(idNumerico); 
              return res.status(200).json(servico);

          } catch (err) {
              console.error("Erro ao obter serviço por ID: ", err);

              if (err.message === "SERVICO_NAO_ENCONTRADO"){
                  return res.status(404).json({ erro: "Serviço não encontrado!" });
              }

              return res.status(500).json({ erro: "Erro interno ao obter serviço!" });
          }
      }
};


module.exports = servicoController;