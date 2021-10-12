CREATE TABLE pessoa(
   codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
   nome VARCHAR(50) NOT NULL,
   ativo TINYINT(1) NOT NULL,
   logradouro VARCHAR(50),
   numero VARCHAR(50),
   complemento VARCHAR(50),
   bairro VARCHAR(50),
   cep VARCHAR(50),
   cidade VARCHAR(50),
   estado VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome,ativo) VALUES ('Jonas', 1);
INSERT INTO pessoa (nome,ativo) VALUES ('Lucas', 1);
INSERT INTO pessoa (nome,ativo) VALUES ('Matheus', 1);
INSERT INTO pessoa (nome,ativo) VALUES ('Bruce', 1);
INSERT INTO pessoa (nome,ativo) VALUES ('Silva', 1);
