
/*
*
* GreeningU:    Script de criação da base de dados
* Versão:       1.1
* Data:         16/05/2015
* Autor:        Jadson de Oliveira Rosa
*
*/

drop database if exists greeningu;

create database greeningu CHARACTER SET utf8 COLLATE utf8_general_ci;

use greeningu;

create table usuario(
	id int auto_increment,
    nome varchar (20) not null,
    sobrenome varchar(30) not null,
    email varchar(30) not null,
    login varchar(10) not null,
    senha varchar(12) not null,
    sexo varchar(1) not null,
    pontuacao int,
    constraint cons_usuario_pk primary key(id)
)engine=INNODB;

create table comunidade(
	id int auto_increment,
    nome varchar (45) not null,
    usuario_lider int,
    constraint cons_comunidade_pk primary key(id),
    constraint cons_comunidade_fk_usuario foreign key(usuario_lider) references usuario(id)
)engine=INNODB;

create table usuario_comunidade(
	data_insercao datetime not null,
    id_usuario int,
    id_comunidade int,
    constraint cons_usuario_comunidade_pk primary key(id_usuario, id_comunidade),
    constraint cons_usuario_comunidade_fk_usuario foreign key(id_usuario) references usuario(id),
    constraint cons_usuario_comunidade_fk_comunidade foreign key(id_comunidade) references comunidade(id)
)engine=INNODB;

create table postagem(
    id int auto_increment,
    titulo varchar(20) not null,
    descricao varchar(100) not null,
    imagem text(15000000),
    constraint cons_postagem_pk primary key(id)
)engine=INNODB;

create table usuario_postagem(
	data_postagem datetime not null,
    id_usuario int not null,
    id_postagem int not null,
    constraint cons_usuario_postagem_pk primary key(id_usuario, id_postagem),
    constraint cons_usuario_postagem_fk_usuario foreign key (id_usuario) references usuario(id),
    constraint cons_usuario_postagem_fk_postagem foreign key (id_postagem) references postagem(id)
)engine=INNODB;

create table voto(
	data_voto datetime,
    id_usuario_votador int not null,
    id_postagem int not null,
    pontos int not null,
	constraint cons_voto_pk primary key(id_usuario_votador, id_postagem),
    constraint cons_voto_fk_usuario foreign key (id_usuario_votador) references usuario(id), 
    constraint cons_voto_fk_postagem foreign key postagem(id_postagem) references postagem(id)
) engine=INNODB;

create table comentario(
	id int auto_increment,
    id_postagem int not null,
    texto varchar(100),
    constraint cons_comentario_pk primary key(id),
    constraint cons_comentario_fk_postagem foreign key(id_postagem) references postagem(id)
) engine=INNODB;

create table usuario_comentario(
	data_comentario datetime not null,
    id_usuario int not null,
    id_comentario int not null,
    constraint cons_usuario_comentario_pk primary key(id_usuario, id_comentario),
    constraint cons_usuario_comentario_fk_usuario foreign key(id_usuario) references usuario(id),
    constraint cons_usuario_comentario_fk_comentario foreign key(id_comentario) references comentario(id)
);

commit;