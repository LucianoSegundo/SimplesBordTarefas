--liquibase formatted sql
--changeset luciano:202503141436
--comment: cardes table create

CREATE TABLE cardes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    dataCriacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    board_column_id BIGINT NOT NULL,
    CONSTRAINT board_columns__cardes_fk FOREIGN KEY (board_column_id) REFERENCES board_columns(id) ON DELETE CASCADE
)ENGINE=InnoDB;

--rollback DROP TABLE cardes
