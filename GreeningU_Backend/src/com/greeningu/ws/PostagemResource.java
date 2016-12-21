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
import com.greeningu.bean.MensagemPadrao;
import com.greeningu.bean.Postagem;
import com.greeningu.bean.PostagemSimplificada;
import com.greeningu.dao.ComunidadeDAO;
import com.greeningu.dao.PostagemDAO;
import com.greeningu.log.Log;

@Path("postagem")
public class PostagemResource {
	
	private static final String NOME_CLASSE = "PostagemResource";
	private static final String METODO_BUSCAR_POSTAGEM = "buscar()";
	
	@POST
	@Path("/inserir")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String salvarPostagem(String json){
		
		Postagem postagem = new Gson().fromJson(json, Postagem.class);
		PostagemDAO dao = new PostagemDAO();
		
		if(dao.salvar(postagem) == 1){
			MensagemPadrao mp = new MensagemPadrao();
			mp.setStatus("OK");
			mp.setInfo("Postagem salva com sucesso.");
			return new Gson().toJson(mp);
		} else {
			MensagemPadrao mp = new MensagemPadrao();
			mp.setStatus("ERRO");
			mp.setInfo("Falha ao inserir postagem");
			return new Gson().toJson(mp);
		}
		
	}
	
	@GET
	@Path("/listarSimplificadas/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public String listarSimpificadas(@PathParam("idUsuario") Integer idUsuario){
		
		PostagemDAO dao = new PostagemDAO();
		ArrayList<PostagemSimplificada> lista = new ArrayList<PostagemSimplificada>();
		lista = dao.listarPostagensSimplificadas(idUsuario);
		
		if(lista.size() > 0){
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			
			return gson.toJson(lista);
		} else {
			MensagemPadrao mp = new MensagemPadrao();
			mp.setStatus("Não foram encontradas postagens para a comunidade");
			return new Gson().toJson(mp);
		}
	}
	
	@GET
	@Path("/buscaPostagem/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String buscaPostagem(@PathParam("id") Integer id){
		try{
			PostagemDAO dao = new PostagemDAO();
			Postagem result = new Postagem();
			result = dao.buscar(id);
			
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			//Gson gson = new Gson();
			String post = gson.toJson(result);
			
			System.out.println("Titulo: " + result.getTitulo() + " IdPost: " + result.getId() );
			return post;
			
		}catch(Exception e){
			Log.erro(NOME_CLASSE, METODO_BUSCAR_POSTAGEM,  e);
			return null;
		}
	}
	

}
