insert into 
	usuario(nome, sobrenome, email, login, senha, sexo, pontuacao)
values 
	('Joao','da Silva','joao@hotmail.com','joao','joao123','M',0),
	('Maria','Margarina','maria@hotmail.com','maria','maria123','F',0),
    ('Fernanda','Strawberry','fernanda@hotmail.com','fernanda','fernanda123','F',0),
    ('Marcelo','Martelo','marcelo@hotmail.com','marcelo','marcelo123','M',0),
    ('Paulo','Paulada','paulo@gmail.com','paulo','paulo123','M',0),
    ('Robinson','Crusoe','robinson@gmail.com','robinson','robinson123','M',0),
    ('Lara','Croft','lara@gmail.com','lara','lara123','F',0),
    ('Monalisa','Lisa','monalisa@gmail.com','monalisa','monalisa123','F',0);

insert into 
	comunidade(nome, usuario_lider)
values 
	('Os comunitários',1),
    ('Os sustentáveis',2),
    ('GreenMasters',3);

insert into 
	usuario_comunidade(data_insercao, id_usuario, id_comunidade)
values
	(sysdate(), 1, 1),
    (sysdate(), 2, 2),
    (sysdate(), 3, 3),
    (sysdate(), 4, 1),
    (sysdate(), 5, 2),
    (sysdate(), 6, 3),
    (sysdate(), 7, 1),
    (sysdate(), 8, 2);

/*insert into postagem(titulo, descricao, imagem)
values (?,?,?);

insert into usuario_postagem(data_postagem, id_usuario, id_postagem)
values(sysdate(),?,?);

insert into voto(data_voto, id_usuario_votador, id_postagem, pontos)
values(sysdate(), ?,?,?);

insert into comentario(id_usuario_comentador, id_postagem, texto)
values(?,?,?);

insert into usuario_comentario(data_comentario, id_usuario, id_comentario)
values (?,?,?);*/