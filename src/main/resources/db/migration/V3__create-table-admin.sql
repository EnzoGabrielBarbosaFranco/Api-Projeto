create table admins(

    id bigint not null auto_increment,
    nome varchar(30) not null,
    login varchar(12) not null,
    senha varchar(12) not null,

    primary key(id)
);