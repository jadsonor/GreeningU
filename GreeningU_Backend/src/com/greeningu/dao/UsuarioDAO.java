package com.greeningu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.greeningu.bean.Comunidade;
import com.greeningu.bean.Usuario;
import com.greeningu.bean.UsuarioLogin;
import com.greeningu.conexao.Conexao;
import com.greeningu.fabricadeobjetos.ConstrutorDeObjetos;
import com.greeningu.log.Log;

//TODO Usar Log4J em todos os métodos

public class UsuarioDAO extends Dao implements CRUD {

	private static final String NOME_CLASSE = "UsuarioDAO";
	private static final String METODO_BUSCAR_POR_LOGIN = "buscarPorLogin()";
	private static final String METODO_ATUALIZAR_PONTUACAO = "atualizarPontuacao()";
	private static final String METODO_BUSCAR_QTDE_POSTS = "buscarQtdePosts()";
	private static final String METODO_BUSCAR_SEXO = "buscarSexo()";
	private static final String METODO_BUSCAR_PONTUACAO = "buscarPontuacao()";
	
	

	@Override
	public Object buscar(Integer id) {

		abrirConexao();

		Usuario usuario = null;
		Comunidade comunidade = null;

		String select = " select u.id, u.nome, u.sobrenome, u.email, u.login, u.senha, u.sexo, u.pontuacao,"
				+ " c.id as id_comunidade, c.nome as nome_comunidade, c.usuario_lider"
				+ " from usuario u"
				+ " inner join usuario_comunidade uc on u.id = uc.id_usuario"
				+ " inner join comunidade c on uc.id_comunidade = c.id"
				+ " where u.id = ?";
		try {

			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				usuario = ConstrutorDeObjetos.construirUsuario(resultSet,
						new Usuario());

				comunidade = new Comunidade();

				comunidade.setId(resultSet.getInt("id_comunidade"));
				comunidade.setNome(resultSet.getString("nome_comunidade"));
				comunidade.setUsarioLider(resultSet.getInt("usuario_lider"));

				usuario.setComunidade(comunidade);

			}

			Log.sucesso(NOME_CLASSE, METODO_BUSCAR);

		} catch (Exception e) {

			Log.erro(NOME_CLASSE, METODO_BUSCAR, e);

		} finally {

			fecharConexao();

		}

		return usuario;

	}

	@Override
	public List listar() {

		abrirConexao();

		Usuario usuario = null;

		Comunidade comunidade = null;

		List<Usuario> usuarios = new ArrayList<Usuario>();

		String select = " select u.id, u.nome, u.sobrenome, u.email, u.login, u.senha, u.sexo, u.pontuacao,"
				+ " c.id as id_comunidade, c.nome as nome_comunidade, c.usuario_lider"
				+ " from usuario u"
				+ " inner join usuario_comunidade uc on u.id = uc.id_usuario"
				+ " inner join comunidade c on uc.id_comunidade = c.id";

		try {

			preparedStatement = conexao.prepareStatement(select);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				usuario = ConstrutorDeObjetos.construirUsuario(resultSet,
						new Usuario());

				comunidade = new Comunidade();

				comunidade.setId(resultSet.getInt("id_comunidade"));
				comunidade.setNome(resultSet.getString("nome_comunidade"));
				comunidade.setUsarioLider(resultSet.getInt("usuario_lider"));

				usuario.setComunidade(comunidade);

				usuarios.add(usuario);
			}

			Log.sucesso(NOME_CLASSE, METODO_LISTAR);

		} catch (Exception e) {
			Log.erro(NOME_CLASSE, METODO_LISTAR, e);
		} finally {

			fecharConexao();
		}

		return usuarios;
	}

	@Override
	public int salvar(Object objeto) {

		int status = 0;

		int statusUsuario = 0;

		int statusComunidade = 0;

		abrirConexao();

		Usuario usuario = (Usuario) objeto;

		Integer novoIdUsuario = null;

		String insertUsuario = "insert into usuario(nome, sobrenome, email, login, senha, sexo, pontuacao)"
				+ "values (?,?,?,?,?,?,?)";
		try {
			// Iserindo usuario
			preparedStatement = conexao.prepareStatement(insertUsuario);

			preparedStatement.setString(1, usuario.getNome());
			preparedStatement.setString(2, usuario.getSobrenome());
			preparedStatement.setString(3, usuario.getEmail());
			preparedStatement.setString(4, usuario.getLogin());
			preparedStatement.setString(5, usuario.getSenha());
			preparedStatement.setString(6, usuario.getSexo());
			preparedStatement.setInt(7, 0);

			statusUsuario = preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement
					.executeQuery("SELECT LAST_INSERT_ID()");
			if (resultSet.next()) {

				novoIdUsuario = resultSet.getInt("LAST_INSERT_ID()");
			}

			// Inserindo registro na tabela usuario_comunidade
			if (usuario.getComunidade() != null) {
				String insertUsuarioComunidade = "insert into usuario_comunidade(data_insercao, id_usuario, id_comunidade)"
						+ " values(sysdate(),?,?)";
				preparedStatement = conexao
						.prepareStatement(insertUsuarioComunidade);
				preparedStatement.setInt(1, novoIdUsuario);
				preparedStatement.setInt(2, usuario.getComunidade().getId());

				statusComunidade = preparedStatement.executeUpdate();

			}

			Log.sucesso(NOME_CLASSE, METODO_SALVAR);

		} catch (Exception e) {
			Log.erro(NOME_CLASSE, METODO_SALVAR, e);
		} finally {

			fecharConexao();
		}

		if (statusComunidade == 0 && statusUsuario == 0) {
			status = 0;
		} else if (statusComunidade == 1 && statusUsuario == 1) {
			status = 1;
		}

		return status;
	}

	@Override
	public int salvar(Object objeto, Map<String, Object> campos) {
		// TODO
		return 0;
	}

	@Override
	public int atualizar(Object objeto) {

		int status = 0;

		abrirConexao();

		Usuario usuario = (Usuario) objeto;

		String update = "update usuario"
				+ " set nome = ?, sobrenome = ?, email = ?, login = ?, senha = ?, sexo = ?, pontuacao = ?"
				+ " where id = ?";

		try {

			preparedStatement = conexao.prepareStatement(update);

			preparedStatement.setString(1, usuario.getNome());
			preparedStatement.setString(2, usuario.getSobrenome());
			preparedStatement.setString(3, usuario.getEmail());
			preparedStatement.setString(4, usuario.getLogin());
			preparedStatement.setString(5, usuario.getSenha());
			preparedStatement.setString(6, usuario.getSexo());
			preparedStatement.setInt(7, usuario.getPontuacao());
			preparedStatement.setInt(8, usuario.getId());

			status = preparedStatement.executeUpdate();

			Log.sucesso(NOME_CLASSE, METODO_ATUALIZAR);

		} catch (Exception e) {

			Log.erro(update, METODO_ATUALIZAR, e);

		} finally {

			fecharConexao();
		}

		return status;
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

	public Usuario buscarPorLogin(String login) {

		abrirConexao();

		Usuario usuario = null;
		Comunidade comunidade = null;

		String select = " select u.id, u.nome, u.sobrenome, u.email, u.login, u.senha, u.sexo, u.pontuacao,"
				+ " c.id as id_comunidade, c.nome as nome_comunidade, c.usuario_lider"
				+ " from usuario u"
				+ " inner join usuario_comunidade uc on u.id = uc.id_usuario"
				+ " inner join comunidade c on uc.id_comunidade = c.id"
				+ " where u.login = ?";
		try {

			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setString(1, login);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				usuario = ConstrutorDeObjetos.construirUsuario(resultSet,
						new Usuario());

				comunidade = new Comunidade();

				comunidade.setId(resultSet.getInt("id_comunidade"));
				comunidade.setNome(resultSet.getString("nome_comunidade"));
				comunidade.setUsarioLider(resultSet.getInt("usuario_lider"));

				usuario.setComunidade(comunidade);

			}

			Log.sucesso(NOME_CLASSE, METODO_BUSCAR_POR_LOGIN);

		} catch (Exception e) {

			Log.erro(NOME_CLASSE, METODO_BUSCAR_POR_LOGIN, e);

		} finally {

			fecharConexao();

		}

		return usuario;

	}

	public int atualizarPontuacao(Usuario usuario, int pontos) {

		int status = 0;

		abrirConexao();

		String update = "update usuario set pontuacao = pontuacao + ? where id = ?";

		try {

			preparedStatement = conexao.prepareStatement(update);

			preparedStatement.setInt(1, pontos);
			preparedStatement.setInt(2, usuario.getId());

			status = preparedStatement.executeUpdate();

			Log.sucesso(NOME_CLASSE, METODO_ATUALIZAR_PONTUACAO);

		} catch (Exception e) {
			Log.erro(NOME_CLASSE, METODO_ATUALIZAR_PONTUACAO, e);
		} finally {
			fecharConexao();
		}

		return status;

	}
	
	public Integer buscarQtdePosts(int idUser){
		
		abrirConexao();
		
		Integer qtde = null;
		String select = "select count(*) from usuario_postagem where id_usuario = ?";
		
		try {
			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, idUser);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				qtde = resultSet.getInt("count(*)");
			}
			
			Log.sucesso(NOME_CLASSE, METODO_BUSCAR_QTDE_POSTS);
		}catch(Exception e){
			
			Log.erro(NOME_CLASSE, METODO_BUSCAR_QTDE_POSTS, e);
			
		}finally{
			fecharConexao();
		}
		return qtde;
	}
	
	public Integer buscarQtdeComunidades(int idUser){
		
		abrirConexao();
		
		Integer qtde = null;
		String select = "select count(*) from usuario_comunidade where id_usuario = ?";
		
		try {
			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, idUser);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				qtde = resultSet.getInt("count(*)");
			}
			
			Log.sucesso(NOME_CLASSE, METODO_BUSCAR_QTDE_POSTS);
		}catch(Exception e){
			
			Log.erro(NOME_CLASSE, METODO_BUSCAR_QTDE_POSTS, e);
			
		}finally{
			fecharConexao();
		}
		return qtde;
	}

	public String buscarNomeComunidade(Integer idUser) {
	
		abrirConexao();
		
		String nomeComunidade = null;
		String select = "select c.nome from usuario_comunidade uc inner join "
				+ "comunidade c where uc.id_comunidade = c.id and uc.id_usuario = ?";
		
		try {
			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, idUser);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				nomeComunidade = resultSet.getString("nome");
			}
			
			Log.sucesso(NOME_CLASSE, METODO_BUSCAR_QTDE_POSTS);
		}catch(Exception e){
			
			Log.erro(NOME_CLASSE, METODO_BUSCAR_QTDE_POSTS, e);
			
		}finally{
			fecharConexao();
		}
		
		
		return nomeComunidade;
	}
	
	public String buscarSexo(Integer id){
		
		abrirConexao();
		
		String sexo = null;
		
		String select = "select sexo from usuario where id = ?";
		
		try {
			
			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				sexo = resultSet.getString("sexo");
			}
			
			Log.sucesso(NOME_CLASSE, METODO_BUSCAR_SEXO);
		} catch (SQLException e) {
			Log.erro(NOME_CLASSE, METODO_BUSCAR_SEXO, e);
		} finally {
			fecharConexao();
		}
		
		return sexo;
	}
	
}
