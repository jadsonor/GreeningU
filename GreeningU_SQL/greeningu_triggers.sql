use greeningu;

delimiter $$ 
create trigger tgr_upd_pontuacao after insert on voto
	for each row
    begin
		update
			usuario 
		set
			pontuacao = pontuacao + new.pontos 
		where	
			id in (
				select
					id_usuario
				from
					usuario_postagem
				where
					id_postagem = new.id_postagem
			);
    end;
    $$
delimiter ;

commit;