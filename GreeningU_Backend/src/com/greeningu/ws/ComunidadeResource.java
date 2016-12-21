package com.greeningu.ws;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.greeningu.bean.Comunidade;
import com.greeningu.bean.Usuario;
import com.greeningu.dao.ComunidadeDAO;
import com.greeningu.log.Log;

@Path("comunidade")
public class ComunidadeResource {
	
	private static final String NOME_CLASSE = "ComunidadeResource";
	private static final String METODO_BUSCAR_QUANTIDADE_MEMBROS = "buscarQuantidadeDeMembros()";
	private static final String METODO_BUSCAR_QUANTIDADE_PONTOS = "buscarQuantidadePontos()";
	private static final String METODO_BUSCAR_NOME_LIDER = "buscarNomeLider()";

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public String listarComunidades(){
		ComunidadeDAO dao = new ComunidadeDAO();

		ArrayList<Comunidade> comunidades= (ArrayList<Comunidade>) dao.listar();

		String lista = new Gson().toJson(comunidades);

		return lista;

	}
	
	@GET
	@Path("/qtdMembros/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String buscarQuantidadeDeMembros(@PathParam("id") Integer id){
		try{
			ComunidadeDAO dao = new ComunidadeDAO();

			Integer qtd = dao.buscarQuantidadeDeMembros(id);
			
			return qtd.toString();
		}catch(Exception e){
			Log.erro(NOME_CLASSE, METODO_BUSCAR_QUANTIDADE_MEMBROS, e);
			return null;
		}

	}
	
	@GET
	@Path("/pontuacaoTotal/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String buscarQuantidadePontos(@PathParam("id") Integer id){
		try{
			ComunidadeDAO dao = new ComunidadeDAO();

			Integer qtd = dao.buscarTotalPontosDosMembros(id);
			
			Log.sucesso(NOME_CLASSE, METODO_BUSCAR_QUANTIDADE_PONTOS);
			
			return qtd.toString();
			
		}catch(Exception e){
			Log.erro(NOME_CLASSE, METODO_BUSCAR_QUANTIDADE_PONTOS, e);
			return null;
		}

	}
	
	@GET
	@Path("/buscarNomeLider/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String buscarNomeLider(@PathParam("id") Integer id){
		try{
			ComunidadeDAO dao = new ComunidadeDAO();
			
			return dao.buscarNomeLider(id);
			
		}catch(Exception e){
			Log.erro(NOME_CLASSE, METODO_BUSCAR_NOME_LIDER, e);
			return null;
		}
	}



}
