DROP TABLE IF EXISTS Geolocations;
DROP TABLE IF EXISTS Places;
DROP TABLE IF EXISTS Ads;
DROP TABLE IF EXISTS Services;
DROP TABLE IF EXISTS Clients;
DROP TABLE IF EXISTS Drivers;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Roles;

CREATE TABLE Roles(
	name VARCHAR(8) PRIMARY KEY
);
INSERT INTO Roles VALUES('GUEST'),('ADMIN'),('DRIVER'),('CLIENT');

CREATE TABLE Users(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	login VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(60) NOT NULL,
    type VARCHAR(8) NOT NULL DEFAULT 'GUEST',
    logged BOOLEAN DEFAULT FALSE,
    CONSTRAINT type_check CHECK (type IN ('GUEST','ADMIN','DRIVER','CLIENT'))
);

CREATE TABLE Drivers(
	user_id BIGINT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    company VARCHAR(255) NOT NULL,
    nip VARCHAR(11) NOT NULL,
    regon VARCHAR(14),
    street VARCHAR(100),
    city VARCHAR(50),
    postcode VARCHAR(10),
    license VARCHAR(20),
    image_url VARCHAR(255),
    displayed_name VARCHAR(50),
    description TEXT,
    card_payment BOOLEAN,
    status TINYINT CHECK(status IN (0,1,2)),
    FOREIGN KEY(user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Clients(
	user_id BIGINT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Services(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	driver_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    price VARCHAR(5),
    FOREIGN KEY(driver_id) REFERENCES Drivers(user_id),
    UNIQUE(driver_id, name)
);

CREATE TABLE Ads(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer VARCHAR(50),
    image_url VARCHAR(255) NOT NULL,
    site_url VARCHAR(255),
    alt_text VARCHAR(50),
    scope VARCHAR(50)
);

CREATE TABLE Places(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    district VARCHAR(50) NOT NULL,
    location POINT NOT NULL,
    driver_id BIGINT NOT NULL,
    FOREIGN KEY(driver_id) REFERENCES Drivers(user_id)
);

CREATE TABLE Geolocations(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    position POINT NOT NULL,
    driver_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY(driver_id) REFERENCES Drivers(user_id),
    SPATIAL INDEX(position)
);
