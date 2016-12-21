package com.greeningu.ws;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greeningu.bean.Comentario;
import com.greeningu.bean.Comunidade;
import com.greeningu.bean.MensagemPadrao;
import com.greeningu.dao.ComentarioDAO;
import com.greeningu.dao.ComunidadeDAO;

@Path("comentario")
public class ComentarioResource {

	@POST
	@Path("/inserirComentario")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String salvarPostagem(String json){
		
		Comentario comentario = new Gson().fromJson(json, Comentario.class);
		ComentarioDAO dao = new ComentarioDAO();
		
		if(dao.salvarComentario(comentario) == 1){
			MensagemPadrao mp = new MensagemPadrao();
			mp.setStatus("OK");
			mp.setInfo("Comentario salvo com sucesso.");
			return new Gson().toJson(mp);
		} else {
			MensagemPadrao mp = new MensagemPadrao();
			mp.setStatus("ERRO");
			mp.setInfo("Falha ao inserir comentario");
			return new Gson().toJson(mp);
		}
	}
	
	@GET
	@Path("/listarComentarios/{idPost}")
	@Produces(MediaType.APPLICATION_JSON)
	public String listarComunidades(@PathParam("idPost") Integer idPost){
		ComentarioDAO dao = new ComentarioDAO();

		ArrayList<Comentario> lista = new ArrayList<Comentario>();
		lista = (ArrayList<Comentario>) dao.listarComentarios(idPost);

		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		
		String comment = gson.toJson(lista);
		
		System.out.println("RETORNANDO LISTA COMENTARIOS:" + comment);
		
		
		return comment;
	}
}
