const express = require("express");
const router = express.Router();
const agendamentoController = require("../controllers/agendamentoController");

router.get("/read", agendamentoController.listarAgendamentos);

module.exports = router;