--liquibase formatted sql
--changeset luciano:202503141446
--comment: bloqueios table create

CREATE TABLE bloqueios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    establoqueado BOOLEAN NOT NULL,
    dataBloqueio TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    motivoBloqueio VARCHAR(255) NOT NULL,
    dataDesbloqueio TIMESTAMP NULL,
    motivoDesbloqueio VARCHAR(255),
    carde_id BIGINT NOT NULL,
    CONSTRAINT cardes__bloqueios_fk FOREIGN KEY (carde_id) REFERENCES cardes(id) ON DELETE CASCADE
)ENGINE=InnoDB;

--rollback DROP TABLE bloqueios
