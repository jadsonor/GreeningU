package com.greeningu.fabricadeobjetos;

import java.sql.ResultSet;

import com.greeningu.bean.Comunidade;
import com.greeningu.bean.Usuario;
import com.greeningu.bean.Voto;
import com.greeningu.log.Log;

public class ConstrutorDeObjetos {
	
	private static final String NOME_CLASSE = "Construtor de objetos";
	
	private static final String METODO_CONSTRUIR_USUARIO = "construirUsuario()";
	
	private static final String METODO_CONSTRUIR_COMUNIDADE = "construirComunidade()";
	
	private static final String METODO_CONSTRUIR_POSTAGEM = "construirPostagem()";
	
	private static final String METODO_CONSTRUIR_VOTO = "construirVoto()";
	
	public static Usuario construirUsuario(ResultSet result, Usuario usuario){
		
		try{
			
			usuario.setId(result.getInt("id"));
			usuario.setNome(result.getString("nome"));
			usuario.setSobrenome(result.getString("sobrenome"));
			usuario.setEmail(result.getString("email"));
			usuario.setLogin(result.getString("login"));
			usuario.setSenha(result.getString("senha"));
			usuario.setSexo(result.getString("sexo"));
			usuario.setPontuacao(result.getInt("pontuacao"));
			
			Log.sucesso(NOME_CLASSE, METODO_CONSTRUIR_USUARIO);
			
		}catch(Exception e){
			
			Log.erro(NOME_CLASSE, METODO_CONSTRUIR_USUARIO, e);
			
		}
		
		return usuario;
	}
	
	
	public static Comunidade construirComunidade(ResultSet result, Comunidade comunidade){
		
		try{
			
			comunidade.setId(result.getInt("id"));
			comunidade.setNome(result.getString("nome"));
			comunidade.setUsarioLider(result.getInt("usuario_lider"));
			
			Log.sucesso(NOME_CLASSE, METODO_CONSTRUIR_COMUNIDADE);
			
		}catch(Exception e){
			
			Log.erro(NOME_CLASSE, METODO_CONSTRUIR_COMUNIDADE, e);
			
		}
		
		return comunidade;
	}
	
public static Voto construirVoto(ResultSet resultSet, Voto voto){
		
		try{
			
			voto.setData(resultSet.getDate("data_voto"));
			voto.setIdPostagem(resultSet.getInt("id_postagem"));
			voto.setIdUsuarioVotador(resultSet.getInt("id_usuario_votador"));
			voto.setPontos(resultSet.getInt("pontos"));
			
			
			Log.sucesso(NOME_CLASSE, METODO_CONSTRUIR_VOTO);
			
		}catch(Exception e){
			
			Log.erro(NOME_CLASSE, METODO_CONSTRUIR_VOTO, e);
			
		}
		
		return voto;
	}
	
}
