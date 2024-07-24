ALTER TABLE medicos ADD COLUMN ativo tinyint;

UPDATE medicos SET ativo = 1;

ALTER TABLE medicos MODIFY COLUMN ativo TINYINT NOT NULL;
