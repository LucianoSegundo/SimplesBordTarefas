--liquibase formatted sql
--changeset luciano:2025031411
--comment: board table create

CREATE TABLE BOARDS(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS
