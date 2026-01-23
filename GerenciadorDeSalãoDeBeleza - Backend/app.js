const express = require('express')
const loginRoutes = require('./src/routes/loginRoutes')
const app = express()
const port = 3000

const cors = require("cors")
const db = require("./db")
const servicoRoutes = require("./src/routes/servicoRoutes");
const clienteRoutes = require("./src/routes/clienteRoutes");
const profissionalRoutes = require("./src/routes/profissionalRoutes");
const agendamentoRoutes = require("./src/routes/agendamentoRoutes");


app.use(cors({ origin: 'http://localhost:5173' }))
app.use(express.json())

app.use('/api/v1/auth', loginRoutes);

app.use("/servicos", servicoRoutes);

app.use("/clientes", clienteRoutes);

app.use("/profissionais", profissionalRoutes);

app.use("/agendamento", agendamentoRoutes);

app.listen(port, () => {
  console.log(`Sistema rodando na porta ${port}`)
})



module.exports = app;

