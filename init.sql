CREATE DATABASE IF NOT EXISTS agenhotel
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE agenhotel;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS hospedes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS quartos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL,
    capacidade INT NOT NULL,
    preco_diaria DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hospede_id INT NOT NULL,
    quarto_id INT NOT NULL,
    data_checkin DATE NOT NULL,
    hora_checkin TIME NOT NULL DEFAULT '14:00:00',
    data_checkout DATE NOT NULL,
    hora_checkout TIME NOT NULL DEFAULT '12:00:00',
    status VARCHAR(30) NOT NULL,

    FOREIGN KEY (hospede_id) REFERENCES hospedes(id),
    FOREIGN KEY (quarto_id) REFERENCES quartos(id)
);

INSERT IGNORE INTO usuarios (nome, email, senha)
VALUES
('Administrador', 'admin@agenhotel.com', '123456'),
('Mariana Souza', 'mariana@agenhotel.com', '123456'),
('Carlos Almeida', 'carlos@agenhotel.com', '123456');

INSERT IGNORE INTO hospedes (nome, email, telefone) VALUES
('Ana Paula Ribeiro', 'ana.ribeiro@email.com', '(34) 99111-2233'),
('Bruno Ferreira Lima', 'bruno.lima@email.com', '(34) 99222-3344'),
('Camila Martins', 'camila.martins@email.com', '(34) 99333-4455'),
('Daniel Oliveira', 'daniel.oliveira@email.com', '(34) 99444-5566'),
('Fernanda Costa', 'fernanda.costa@email.com', '(34) 99555-6677'),
('Gustavo Rocha', 'gustavo.rocha@email.com', '(34) 99666-7788');

INSERT IGNORE INTO quartos (numero, tipo, capacidade, preco_diaria) VALUES
(101, 'Standard', 2, 180.00),
(102, 'Standard', 2, 180.00),
(201, 'Luxo', 3, 290.00),
(202, 'Luxo', 3, 290.00),
(301, 'Suite', 4, 450.00);

-- Reservas de demonstracao. As datas usam o dia atual para continuarem uteis
-- mesmo quando o projeto for instalado novamente em outra data.
INSERT INTO reservas (hospede_id, quarto_id, data_checkin, data_checkout, status)
SELECT h.id, q.id, DATE_SUB(CURDATE(), INTERVAL 1 DAY), DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'CHECK_IN'
FROM hospedes h JOIN quartos q ON q.numero = 101
WHERE h.email = 'ana.ribeiro@email.com'
  AND NOT EXISTS (SELECT 1 FROM reservas r WHERE r.hospede_id = h.id AND r.quarto_id = q.id AND r.status = 'CHECK_IN');

INSERT INTO reservas (hospede_id, quarto_id, data_checkin, data_checkout, status)
SELECT h.id, q.id, DATE_ADD(CURDATE(), INTERVAL 3 DAY), DATE_ADD(CURDATE(), INTERVAL 6 DAY), 'RESERVADA'
FROM hospedes h JOIN quartos q ON q.numero = 201
WHERE h.email = 'bruno.lima@email.com'
  AND NOT EXISTS (SELECT 1 FROM reservas r WHERE r.hospede_id = h.id AND r.quarto_id = q.id AND r.status = 'RESERVADA');

INSERT INTO reservas (hospede_id, quarto_id, data_checkin, data_checkout, status)
SELECT h.id, q.id, DATE_ADD(CURDATE(), INTERVAL 8 DAY), DATE_ADD(CURDATE(), INTERVAL 11 DAY), 'RESERVADA'
FROM hospedes h JOIN quartos q ON q.numero = 301
WHERE h.email = 'camila.martins@email.com'
  AND NOT EXISTS (SELECT 1 FROM reservas r WHERE r.hospede_id = h.id AND r.quarto_id = q.id AND r.status = 'RESERVADA');

INSERT INTO reservas (hospede_id, quarto_id, data_checkin, data_checkout, status)
SELECT h.id, q.id, DATE_SUB(CURDATE(), INTERVAL 8 DAY), DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'CHECK_OUT'
FROM hospedes h JOIN quartos q ON q.numero = 102
WHERE h.email = 'daniel.oliveira@email.com'
  AND NOT EXISTS (SELECT 1 FROM reservas r WHERE r.hospede_id = h.id AND r.quarto_id = q.id AND r.status = 'CHECK_OUT');
