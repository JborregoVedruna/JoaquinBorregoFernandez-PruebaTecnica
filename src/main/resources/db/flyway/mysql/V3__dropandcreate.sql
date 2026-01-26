DROP TABLE IF EXISTS loan_applications;

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    user_uuid CHAR(36) NOT NULL,
    username VARCHAR(45) NOT NULL UNIQUE,
    password CHAR(60) NOT NULL,
    user_dni VARCHAR(9) NOT NULL UNIQUE,
    rol TINYINT DEFAULT 0 NOT NULL,
    account_expiration_date DATETIME NOT NULL,
    is_locked BOOLEAN DEFAULT FALSE NOT NULL,
    credentials_expiration_date DATETIME NOT NULL,
    is_enabled BOOLEAN DEFAULT TRUE NOT NULL,
    PRIMARY KEY (user_uuid)
);

CREATE UNIQUE INDEX username_UNIQUE ON users (username ASC);

CREATE TABLE IF NOT EXISTS loan_applications (
    uuid CHAR(36) NOT NULL,
    requested_amount DECIMAL(13, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status TINYINT DEFAULT 0 NOT NULL,
    users_user_uuid CHAR(36) NOT NULL,
    PRIMARY KEY (uuid),
    CONSTRAINT fk_loan_applications_users FOREIGN KEY (users_user_uuid) REFERENCES users (user_uuid) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX fk_loan_applications_users_idx ON loan_applications (users_user_uuid ASC);