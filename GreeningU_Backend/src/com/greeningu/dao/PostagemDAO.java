package com.greeningu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.greeningu.bean.Comunidade;
import com.greeningu.bean.Postagem;
import com.greeningu.bean.PostagemSimplificada;
import com.greeningu.fabricadeobjetos.ConstrutorDeObjetos;
import com.greeningu.log.Log;

public class PostagemDAO extends Dao implements CRUD {
	
	private static final String NOME_CLASSE = "PostagemDAO";
	
	private static final String METODO_LISTAR_POST_SIMPL = "listarPostagensSimplificadas()";
	
	
	
	@Override
	public Postagem buscar(Integer id) {
		abrirConexao();
		Postagem postagem = new Postagem();
		String select = " select p.id, p.descricao, p.titulo, p.imagem, up.data_postagem" 		
				+ " from postagem p"
				+ " inner join usuario_postagem up"
				+ " on p.id = up.id_postagem"
				+ " inner join usuario u"
				+ " on up.id_usuario = u.id"
				+ " where p.id = ?";

		try{
			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				postagem.setId(resultSet.getInt("id"));
				postagem.setTitulo(resultSet.getString("titulo"));
				postagem.setDescricao(resultSet.getString("descricao"));
				postagem.setImagem(resultSet.getString("imagem"));
				
				Date data = new Date(resultSet.getDate("data_postagem").toString());
				
				postagem.setData(data);
			}
			Log.sucesso(NOME_CLASSE, METODO_BUSCAR);

		}catch(Exception e){
			Log.erro(NOME_CLASSE, METODO_BUSCAR, e);
		}finally{
			fecharConexao();
		}

		return postagem;
	}

	@Override
	public List<Object> listar() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<PostagemSimplificada> listarPostagensSimplificadas(Integer idUsuario) {
		
		abrirConexao();
		
		ArrayList<PostagemSimplificada> postagens = new ArrayList<PostagemSimplificada>();
		
		String select = "select * from"
				+ " postagem p"
				+ " inner join usuario_postagem up"
				+ " on p.id = up.id_postagem"
				+ " inner join usuario u"
				+ " on u.id = up.id_usuario"
				+ " inner join usuario_comunidade uc"
				+ " on u.id = uc.id_usuario"
				+ " inner join comunidade c"
				+ " on uc.id_comunidade = c.id"
				+ " where u.id <> ?"
//				+ " and p.id not in ("
//					+ " select id_postagem from"
//					+ " voto vo"
//					+ " where vo.id_usuario_votador = ?"
//				+ " )"
				+ " and c.id in ("
				+ "     select uc.id_comunidade"
				+ "     from usuario u"
				+ "     inner join usuario_comunidade uc"
				+ "     on u.id = uc.id_usuario"
				+ "     where uc.id_usuario = ?"
				+ " )";
		try {
			
			preparedStatement = conexao.prepareStatement(select);
			
			preparedStatement.setInt(1, idUsuario);
			preparedStatement.setInt(2, idUsuario);
			//preparedStatement.setInt(3, idUsuario);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				PostagemSimplificada postagemSimplificada = new  PostagemSimplificada();
				
				String nomeUsuario = resultSet.getString("nome") + " " + resultSet.getString("sobrenome");
				postagemSimplificada.setId(resultSet.getInt("id_postagem"));
				postagemSimplificada.setNomeUsuario(nomeUsuario);
				postagemSimplificada.setTitulo(resultSet.getString("titulo"));
				postagemSimplificada.setDataPostagem(resultSet.getDate("data_postagem"));
				
				System.out.println(postagemSimplificada.getDataPostagem());
				postagens.add(postagemSimplificada);
			}
			
		} catch (SQLException e) {
			Log.erro(NOME_CLASSE, METODO_LISTAR_POST_SIMPL, e);
		} finally{
			fecharConexao();
		}
		
		return postagens;
	}

	@Override
	public int salvar(Object objeto) {
		
		abrirConexao();
		
		int resultado = 0;
		int resultadoTemp = 0;
		int novoIdPostagem = 0;
		
		Postagem postagem = (Postagem) objeto;
		
		String insert = "insert into postagem(titulo, descricao, imagem)"
				+ " values (?,?,?)";
		try {
			
			preparedStatement = conexao.prepareStatement(insert);
			preparedStatement.setString(1, postagem.getTitulo());
			preparedStatement.setString(2, postagem.getDescricao());
			preparedStatement.setString(3, postagem.getImagem());
			
			resultadoTemp = preparedStatement.executeUpdate();
			
			ResultSet resultSet = preparedStatement
					.executeQuery("SELECT LAST_INSERT_ID()");
			if (resultSet.next()) {

				novoIdPostagem = resultSet.getInt("LAST_INSERT_ID()");
			}
			
			insert = "insert into usuario_postagem(data_postagem, id_usuario, id_postagem)"
					+ " values(sysdate(),?,?)";
			
			preparedStatement = conexao.prepareStatement(insert);
			preparedStatement.setInt(1, postagem.getIdUsuario());
			preparedStatement.setInt(2, novoIdPostagem);
			
			resultado = preparedStatement.executeUpdate();
			
			Log.sucesso(NOME_CLASSE, METODO_SALVAR);
			
		} catch (SQLException e) {
			
			Log.erro(NOME_CLASSE, METODO_SALVAR, e);
			
		} finally {
			fecharConexao();
		}
		
		return resultado & resultadoTemp;
	}

	@Override
	public int salvar(Object objeto, Map<String, Object> campos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int atualizar(Object objeto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int atualizar(Object objeto, Map<String, Object> campos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int excluir(Object objeto) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	

}
