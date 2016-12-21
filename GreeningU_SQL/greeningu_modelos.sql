-- usuario

select *
from usuario;

select *
from usuario
where id = ?;

insert into usuario(nome, sobrenome, email, login, senha, sexo, pontuacao)
values (?,?,?,?,?,?,?);

update usuario
set nome = ?, sobrenome = ?, email = ?, login = ?, senha = ?, sexo = ?, pontuacao = ?
where id = ?;

delete from usuario
where id = ?;
-- ===================================================================================

-- comunidade

select *
from comunidade;


select *
from comunidade
where id = ?;

insert into comunidade(nome, usuario_lider)
values (?,?);

update comunidade
set nome = ?, usuario_lider = ?
where id = ?;

delete from comunidade
where id = ?;
-- ===================================================================================

-- usuario_comunidade

select * 
from usuario_comunidade;

insert into usuario_comunidade(data_insercao, id_usuario, id_comunidade)
values(sysdate(), ?, ?);

update usuario_comunidade
set data_insercao = ? -- [, id_usuario = ?, id_comunidade = ?]
where id_usuario = ? and id_comunidade = ?;

delete from usuario_comunidade
where id_usuario = ? and id_comunidade = ?;
-- ===================================================================================

-- postagem

select *
from postagem;


select *
from postagem
where id = ?;

insert into postagem(titulo, descricao, imagem)
values (?,?,?);

update postagem
set titulo = ?, descricao = ?, imagem = ?
where id = ?;

delete from postagem
where id = ?;
-- ===================================================================================

-- usuario_postagem

select * 
from usuario_postagem;

insert into usuario_postagem(data_postagem, id_usuario, id_postagem)
values(sysdate(),?,?);

update usuario_postagem
set data_postagem = ? -- , [id_usuario, id_postagem]
where id = ?;

delete from usuario_postagem
where id_usuario = ? and id_postagem = ?;
-- ===================================================================================

-- voto

select *
from voto;

select *
from voto
where id = ?;

insert into voto(data_voto, id_usuario_votador, id_postagem, pontos)
values(sysdate(), ?,?,?);

update voto
set data_voto = ?, id_usuario_votador = ?, id_postagem = ?, pontos = ?
where id = ?;

delete from voto
where id = ?;

-- ===================================================================================

-- comentario

select *
from comentario;

select *
from comentario
where id = ?;

insert into comentario(id_usuario_comentador, id_postagem, texto)
values(?,?,?);

update comentario
set id_usuario_comentador = ?, id_postagem = ?, texto = ?;

delete from comentario
where id = ?;
-- ===================================================================================

-- usuario_comentario

select *
from usuario_comentario;

insert into usuario_comentario(data_comentario, id_usuario, id_comentario)
values (?,?,?);

update usuario_comentario
set data_comentario = ?, id_usuario = ?, id_comentario = ?
where id_usuario = ? and id_comentario = ?;

delete from usuario_comentario
where id_usuario = ? and id_comentario = ?;

-- ===================================================================================