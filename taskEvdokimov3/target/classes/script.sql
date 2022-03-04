DROP SCHEMA csv;

CREATE SCHEMA IF NOT EXISTS csv;

USE csv;

CREATE TABLE IF NOT EXISTS users  (
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  application VARCHAR(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  is_active BOOLEAN NOT NULL,
  job_title VARCHAR(50),
  department VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS supply (
  id_doc BIGINT NOT NULL PRIMARY KEY,
  doc_date DATE NOT NULL,
  posting_date DATE NOT NULL,
  name_user VARCHAR(45) NOT NULL,
  is_authorized BOOLEAN NOT NULL,
  id_user INT,
  FOREIGN KEY (id_user) references users(id)
);

CREATE TABLE IF NOT EXISTS product (
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name_product VARCHAR(60) NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  unit_measure VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS supply_product (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    id_supply  BIGINT NOT NULL,
    id_product INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (id_supply) REFERENCES supply(id_doc) ON UPDATE CASCADE,
    FOREIGN KEY (id_product) REFERENCES product(id) ON UPDATE CASCADE
);