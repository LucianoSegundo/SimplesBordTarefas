--liquibase formatted sql
--changeset luciano:2025031414
--comment: boardcolumn table create

CREATE TABLE board_columns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo CHAR(10) NOT NULL,
    ordem INT NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT bords__board_columns_fk FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE,
    CONSTRAINT id_ordem_uk UNIQUE KEY unique_board_id_ordem (board_id, ordem)
)ENGINE=InnoDB;

--rollback DROP TABLE board_columns
