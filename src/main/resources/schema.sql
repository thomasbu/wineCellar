### Author: Thomas Bulens
### Date : 14/03/2024

CREATE SCHEMA IF NOT EXISTS mywinecellar;

SET NAMES 'UTF8MB4';

USE mywinecellar;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50)     NOT NULL,
    last_name  VARCHAR(50)     NOT NULL,
    email      VARCHAR(100)    NOT NULL,
    password   VARCHAR(255) DEFAULT NULL,
    address    VARCHAR(255) DEFAULT NULL,
    phone      VARCHAR(30)  DEFAULT NULL,
    title      VARCHAR(50)  DEFAULT NULL,
    bio        VARCHAR(255) DEFAULT NULL,
    enabled    BOOLEAN      DEFAULT FALSE,
    non_locked BOOLEAN      DEFAULT TRUE,
    created_at DATETIME     DEFAULT CURRENT_TIMESTAMP,
    image_url  VARCHAR(255) DEFAULT 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.m.wikipedia.org%2Fwiki%2FFile%3ASample_User_Icon.png&psig=AOvVaw3fFyElpR6ewVfuA3cM9KTg&ust=1710513450197000&source=images&cd=vfe&opi=89978449&ved=0CBMQjRxqFwoTCPjTwaX984QDFQAAAAAdAAAAABAE',
    CONSTRAINT UQ_Users_Email UNIQUE (email)
);

DROP TABLE IF EXISTS Roles;

CREATE TABLE Roles
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50)     NOT NULL,
    permission VARCHAR(255)    NOT NULL,
    CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

DROP TABLE IF EXISTS AccountVerifications;

CREATE TABLE AccountVerifications
(
    id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    url     VARCHAR(255)    NOT NULL,
    -- date     DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_AccountVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_AccountVerifications_Url UNIQUE (url)
);

DROP TABLE IF EXISTS ResetPasswordVerifications;

CREATE TABLE ResetPasswordVerifications
(
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT UNSIGNED NOT NULL,
    url             VARCHAR(255)    NOT NULL,
    expiration_date DATETIME        NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_ResetPasswordVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_ResetPasswordVerifications_Url UNIQUE (url)
);

DROP TABLE IF EXISTS WineCellars;

CREATE TABLE wineCellars
(
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255)    NOT NULL,
    description     VARCHAR(255) DEFAULT NULL,
    capacity        INT          DEFAULT 0,
    temperature_min INT          DEFAULT NULL,
    temperature_max INT          DEFAULT NULL,
    type            VARCHAR(255) DEFAULT NULL,
    location        VARCHAR(255) DEFAULT NULL,
    image_url       VARCHAR(255) DEFAULT NULL,
    created_at      DATETIME     DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS UserWineCellars;

CREATE TABLE UserWineCellars
(
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT UNSIGNED NOT NULL,
    winecellar_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (winecellar_id) REFERENCES WineCellars (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT UQ_UserWineCellars_User_Id UNIQUE (user_id)
);

DROP TABLE IF EXISTS Compartments;

CREATE TABLE Compartments
(
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description   VARCHAR(255) DEFAULT NULL,
    type          VARCHAR(255) DEFAULT NULL,
    capacity      INT          DEFAULT 0,
    winecellar_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (winecellar_id) REFERENCES WineCellars (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Bottles;

CREATE TABLE Bottles
(
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255) DEFAULT NULL,
    description    VARCHAR(255) DEFAULT NULL,
    appellation    VARCHAR(255) DEFAULT NULL,
    year           DATETIME     DEFAULT NULL,
    color          VARCHAR(255) DEFAULT NULL,
    alcohol        INT          DEFAULT NULL,
    volume         INT          DEFAULT NULL,
    rating         INT          DEFAULT NULL,
    note           VARCHAR(255) DEFAULT NULL,
    image          VARCHAR(255) DEFAULT NULL,
    buy_at         DATETIME     DEFAULT NULL,
    opened_at      DATETIME     DEFAULT NULL,
    compartment_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (compartment_id) REFERENCES Compartments (id) ON DELETE RESTRICT ON UPDATE CASCADE
);