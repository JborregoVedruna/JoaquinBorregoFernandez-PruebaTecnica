CREATE SCHEMA IF NOT EXISTS caixabank;

USE caixabank;

DROP TABLE IF EXISTS loan_applications;

CREATE TABLE loan_applications (
    uuid CHAR(36) NOT NULL,
    applicant_name VARCHAR(45) NOT NULL,
    requested_amount DECIMAL(13, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    applicant_dni VARCHAR(9) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status TINYINT DEFAULT 0 NOT NULL,
    PRIMARY KEY (uuid)
);