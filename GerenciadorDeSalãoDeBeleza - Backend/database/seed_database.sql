-- adicionando dados mockados ao banco (que não fazem parte do crud)
-- Desativa a verificação de chaves estrangeiras temporariamente para poder limpar as tabelas
SET FOREIGN_KEY_CHECKS = 0;

-- TRUNCATE TABLE Profissional_Serviço;
-- TRUNCATE TABLE Serviço_Agendamento;
-- TRUNCATE TABLE Pagamento;
-- TRUNCATE TABLE Agendamento;
-- TRUNCATE TABLE Serviço;

-- trunca se for inserir essas tabelas aqui

TRUNCATE TABLE Profissional;
TRUNCATE TABLE Cliente;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO Cliente (nome, CPF, telefone, email) VALUES
('Juliana Moura', '22345678901', '11999990001', 'ju@gmail.com'),
('Marina Sales', '32345678901', '11999990002', 'marina@gmail.com'),
('Layla Souza', '42345678901', '11999990003', 'layla@gmail.com'),
('Jeniffer Queiroz', '52345678901', '11999990004', 'jeniffer@gmail.com'),
('Larissa Silva', '62345678901', '11999990005', 'larissa@gmail.com'),
('Letícia Gomes', '72345678901', '11999990006', 'leticia@gmail.com'),
('Rafaela Gomes', '82345678901', '11999990007', 'rafaela@gmail.com'),
('Isabele Fortunato', '92345678901', '11999990008', 'isabele@gmail.com'),
('Thais Fernandes', '12345678902', '11999990009', 'thais@gmail.com'),
('Sofia Costa', '12345678903', '11999990011', 'sofia@gmail.com'),
('Yasmin Silva', '12345678904', '11999990021', 'yasmin@gmail.com'),
('Luiza Santos', '12345678905', '11999990031', 'luiza@gmail.com'),
('Aurora Carvalho', '12345678906', '11999990041', 'aurora@gmail.com'),
('Elisa Campos', '12345678907', '11999990051', 'elisa@gmail.com'),
('Valeria Mendes', '12345678908', '11999990061', 'valeria@gmail.com'),
('Ana Souza', '12345678909', '11999990071', 'ana@gmail.com'),
('Beatriz Lima', '98765432100', '11999990081', 'bia@gmail.com');

INSERT INTO Profissional (nome, CPF, especialidade, telefone) VALUES
('Carlos Silva', '21122233344', 'Cabelos', '11988880001'),
('Álvaro Campos', '31122233344', 'Cabelos', '11988880002'),
('Luana Morais', '41122233344', 'Sobrancelhas', '11988880003'),
('Paula Carvalho', '51122233344', 'Maquiagem', '11988880004'),
('Mariana Costa', '55566677788', 'Unhas', '11988880005');