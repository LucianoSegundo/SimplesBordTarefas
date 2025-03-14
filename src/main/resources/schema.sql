CREATE DATABASE bancoBoard;

USE bancoBoard;


CREATE TABLE Board (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);


CREATE TABLE BoardColumn (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    ordem INT NOT NULL,
    board_id BIGINT,
    FOREIGN KEY (board_id) REFERENCES Board(id)
);


CREATE TABLE Card (
    id BIGINT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    dataCriacao TIMESTAMP NOT NULL,
    board_column_id BIGINT,
    FOREIGN KEY (board_column_id) REFERENCES BoardColumn(id)
);


CREATE TABLE Bloqueio (
    id BIGINT PRIMARY KEY,
    bloqueado BOOLEAN NOT NULL,
    dataBloqueio TIMESTAMP NOT NULL,
    motivoBloqueio VARCHAR(255) NOT NULL,
    dataDesbloqueio TIMESTAMP,
    motivoDesbloqueio VARCHAR(255),
    card_id BIGINT,
    FOREIGN KEY (card_id) REFERENCES Card(id)
);

