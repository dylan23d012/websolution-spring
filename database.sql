-- Base de datos del proyecto Web Solution
CREATE DATABASE IF NOT EXISTS websolution;

USE websolution;

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    telefono VARCHAR(30),
    empresa VARCHAR(100)
);

-- Datos de ejemplo
INSERT INTO clientes (nombre, email, telefono, empresa)
VALUES
('Cliente Ejemplo', 'ejemplo@websolution.com', '3001234567', 'Web Solution');
