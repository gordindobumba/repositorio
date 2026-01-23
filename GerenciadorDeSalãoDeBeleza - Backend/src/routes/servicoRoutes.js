//forma padrao de routes, indo pro app.js

const express = require("express");
const router = express.Router();
const servicoController = require("../controllers/servicoController");

router.post("/create", servicoController.criarServico);
router.delete("/delete", servicoController.removerServico);
router.put("/update", servicoController.atualizarServico);
router.get("/read", servicoController.listarServicos);
router.get("/read/:id", servicoController.obterServicoPorId);


module.exports = router;