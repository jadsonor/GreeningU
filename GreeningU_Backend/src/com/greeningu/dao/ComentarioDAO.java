package com.greeningu.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.greeningu.bean.Comentario;
import com.greeningu.bean.PostagemSimplificada;
import com.greeningu.log.Log;

public class ComentarioDAO extends Dao implements CRUD{

	private static final String NOME_CLASSE = "ComentarioDAO";
	private static final String METODO_LISTAR_COMENTARIO = "listarComentario()";
	
	public int salvarComentario(Comentario comentario) {

		int status = 0;

		abrirConexao();

		String insert = "insert into comentario (id_postagem, texto) values (?, ?)";
		
		String insert2 = "insert into usuario_comentario (data_comentario, id_usuario, id_comentario) values (sysdate(), ?, LAST_INSERT_ID())";
		
		try {

			preparedStatement = conexao.prepareStatement(insert);
			preparedStatement.setInt(1, comentario.getIdPostagem());
			preparedStatement.setString(2, comentario.getTexto());

			status = preparedStatement.executeUpdate();
			
			System.out.println("1 - STATUS: " +  status);
			
			preparedStatement = conexao.prepareStatement(insert2);
			preparedStatement.setInt(1, comentario.getIdUsuario());

			status = preparedStatement.executeUpdate();
			
			System.out.println("2 - STATUS: " +  status);

			Log.sucesso(NOME_CLASSE, METODO_SALVAR);

		} catch (Exception e) {

			Log.erro(NOME_CLASSE, METODO_SALVAR, e);

		}finally{

			fecharConexao();
			
		}
		return status;
	}
	
	public ArrayList<Comentario> listarComentarios(Integer idPost) {
		
		abrirConexao();
		
		ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
		
		String select = "select texto, data_comentario from comentario c "
				+ "inner join usuario_comentario uc on c.id = uc.id_comentario where c.id_postagem = ?;";
		
		System.out.println("BUSCANDO COMENTARIOS");
		try {
			
			preparedStatement = conexao.prepareStatement(select);
			
			preparedStatement.setInt(1, idPost);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				Comentario comentario = new  Comentario();
				
				comentario.setData(resultSet.getDate("data_comentario"));
				comentario.setTexto(resultSet.getString("texto"));
				
				System.out.println("Comentario: " + comentario.getData() + ", " + comentario.getTexto());
				comentarios.add(comentario);
			}
			
		} catch (SQLException e) {
			Log.erro(NOME_CLASSE, METODO_LISTAR_COMENTARIO, e);
		} finally{
			fecharConexao();
		}
		
		return comentarios;
	}
	

	@Override
	public Object buscar(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int salvar(Object objeto) {
		// TODO Auto-generated method stub
		return 0;
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
