alter table consultas add column cancelado tinyint default 0;

alter table consultas modify column cancelado tinyint not null;

