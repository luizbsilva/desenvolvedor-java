create table if not exists cliente
(
  id bigserial not null
    constraint cliente_pkey
    primary key,
  admin boolean not null,
  email varchar(255) not null
    constraint uk_emailcliente
    unique,
  cpf varchar(255) not null
    constraint uk_cpfcliente
    unique,
  bairro varchar(255),
  cep varchar(255),
  cidade varchar(255),
  estado varchar(255),
  rua varchar(255),
  nome varchar(255) not null,
  tipo varchar(255) not null,
  senha varchar(255) not null,
  data_atualizacao date,
  data_criacao date
);

alter table cliente owner to postgres;

create table if not exists categoria
(
  id bigserial not null
    constraint categoria_pkey
    primary key,
  categoria varchar(255)
);

alter table categoria owner to postgres;

create table if not exists pedido
(
  id bigserial not null
    constraint pedido_pkey
    primary key,
  data bytea,
  sessao varchar(255),
  status varchar(255),
  cliente_id bigint
    constraint fk_pedido_cliente
    references cliente
);

alter table pedido owner to postgres;

create table if not exists produto
(
  id bigserial not null
    constraint produto_pkey
    primary key,
  descricao varchar(255),
  foto oid,
  preco numeric(19,2),
  produto varchar(255),
  quantidade integer,
  quantidade_reserva integer default 0,
  categoria_id bigint
    constraint fk_produto_categoria
    references categoria
);

alter table produto owner to postgres;

create table if not exists pedido_item
(
  id bigserial not null
    constraint pedido_item_pkey
    primary key,
  quantidade integer not null,
  sub_total numeric(19,2),
  valor numeric(19,2),
  pedido_id bigint
    constraint fk_pedido_item_pedido
    references pedido,
  produto_id bigint
    constraint fk_pedido_item_produto
    references produto
);

alter table pedido_item owner to postgres;


