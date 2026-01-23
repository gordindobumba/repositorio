const express = require("express");
const router = express.Router();
const profissionalController = require("../controllers/profissionalController");

router.get("/read", profissionalController.listarProfissionais);

module.exports = router;