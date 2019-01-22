CREATE DATABASE IF NOT EXISTS ControleVenda;

USE ControleVenda;

DROP TABLE IF EXISTS pedido;

CREATE TABLE IF NOT EXISTS pedido (
  id int AUTO_INCREMENT PRIMARY KEY,
  controle int default NULL,
  data datetime not null DEFAULT NOW(),
  produto varchar(50) DEFAULT NULL,
  valorunitario decimal(10,2) DEFAULT NULL,  
  quantidade int DEFAULT NULL,
  codigocliente varchar(7) DEFAULT NULL,
  total decimal(15,2) DEFAULT NULL
);