ALTER TABLE pacientes ADD COLUMN ativo TINYINT DEFAULT 1;

UPDATE pacientes SET ativo = 1;

ALTER TABLE pacientes MODIFY COLUMN ativo TINYINT NOT NULL;
