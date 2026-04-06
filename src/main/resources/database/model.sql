CREATE DATABASE db_automotivo;

USE db_automotivo;

show tables;
DESCRIBE tbl_veiculos;
SELECT * FROM tbl_veiculos;

CREATE TABLE tbl_veiculos (
	id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(200) NOT NULL,
    ano INT NOT NULL,
    marca VARCHAR(200) NOT NULL,
    quilometragem DECIMAL(5, 3),
    combustivel VARCHAR(100),
    disponibilidade VARCHAR(50),
    preco FLOAT(53) NOT NULL,
    foto VARCHAR(200) NOT NULL
);


INSERT INTO tbl_veiculos(modelo, ano, marca, quilometragem, combustivel, disponibilidade, preco, foto)
VALUES(
	'NINJA 650',
    '2025',
    'Kawasaki',
    '0.00',
    'Gasolina sem Chumbo',
    'Disponível',
    '50890.00',
    'https://th.bing.com/th/id/R.449f5665a774c89429dbf5b854ecb3bc?rik=xlKyhzDr7wWaQQ&pid=ImgRaw&r=0'
);

INSERT INTO tbl_veiculos(modelo, ano, marca, quilometragem, combustivel, disponibilidade, preco, foto)
VALUES(
	'F80',
    '2013',
    'Ferrari',
    '0.00',
    'Gasolina',
    'Disponível',
    '127500.00',
	'https://i.pinimg.com/736x/75/d0/75/75d075898ada6f138a364bbc3127a9e0.jpg'
);


INSERT INTO tbl_veiculos(modelo, ano, marca, quilometragem, combustivel, disponibilidade, preco, foto)
VALUES(
	'Cybertruck',
    '2024',
    'Tesla',
    '0.00',
    'Elétrico',
    'Indisponível',
    '349990.00',
    'https://img-new.cgtrader.com/items/2779027/18a776e3d8/large/tesla-cybertruck-313-mm-wheelbase-3d-model-stl.jpg'
);


INSERT INTO tbl_veiculos(modelo, ano, marca, quilometragem, combustivel, disponibilidade, preco, foto)
VALUES(
	'EQB 350 4MATIC AMG Line',
    '2022',
    'Mercedes Benz',
    '40',
    'Elétrico',
    'Disponível',
    '450000.00',
	'https://th.bing.com/th/id/OIP.sIxQ3kqjuugb9X-yfOzZCQHaFM?rs=1&pid=ImgDetMain'
);