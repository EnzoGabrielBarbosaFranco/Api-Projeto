create table produtos(

    id bigint not null auto_increment,
    nome varchar(34) not null,
    descricao varchar(250) not null,
    cor varchar(10) not null,
    quantidade varchar(7) not null,
    tamanho varchar(5),
    valor DECIMAL(10,2) not null,
    categoria varchar(100) not null,

    primary key(id)
);