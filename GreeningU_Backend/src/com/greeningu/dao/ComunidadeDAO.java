package com.greeningu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.greeningu.bean.Comunidade;
import com.greeningu.conexao.Conexao;
import com.greeningu.fabricadeobjetos.ConstrutorDeObjetos;
import com.greeningu.log.Log;

public class ComunidadeDAO extends Dao implements CRUD{

	private static final String NOME_CLASSE = "ComunidadeDAO";
	private static final String METODO_BUSCAR_QTD_MEMBROS = "buscarQuantidadeDeMembros()";
	private static final String METODO_BUSCAR_TOTAL_PONTOS = "buscarTotalPontosDosMembros";
	private static final String METODO_BUSCAR_NOME_LIDER = "buscarNomeLider()";

	@Override
	public Object buscar(Integer id) {

		abrirConexao();
		Comunidade comunidade = null;
		String select = "select * from comunidade where id = ?";

		try{

			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){

				comunidade = ConstrutorDeObjetos.construirComunidade(resultSet, new Comunidade());

			}

			Log.sucesso(NOME_CLASSE, METODO_BUSCAR);

		}catch(Exception e){

			Log.erro(NOME_CLASSE, METODO_BUSCAR, e);

		}finally{

			fecharConexao();
		}

		return comunidade;
	}

	@Override
	public List listar() {

		abrirConexao();
		Comunidade comunidade = null;
		ArrayList<Comunidade> comunidades = new ArrayList<Comunidade>();
		String select = "select * from comunidade";

		try {

			preparedStatement = conexao.prepareStatement(select);
			resultSet = preparedStatement.executeQuery(select);

			while(resultSet.next()){

				comunidade = ConstrutorDeObjetos.construirComunidade(resultSet, new Comunidade());
				comunidades.add(comunidade);

			}

			Log.sucesso(NOME_CLASSE, METODO_LISTAR);

		} catch (Exception e) {

			Log.erro(NOME_CLASSE, METODO_LISTAR, e);

		}finally{

			fecharConexao();
		}

		return comunidades;
	}

	@Override
	public int salvar(Object objeto) {

		int status = 0;

		abrirConexao();

		Comunidade comunidade = (Comunidade)objeto;
		String insert = "insert into comunidade(nome, usuario_lider)"
				+ " values (?,?)";

		try {

			preparedStatement = conexao.prepareStatement(insert);
			preparedStatement.setString(1, comunidade.getNome());
			preparedStatement.setInt(2, comunidade.getUsarioLider());

			status = preparedStatement.executeUpdate();

			Log.sucesso(NOME_CLASSE, METODO_SALVAR);

		} catch (Exception e) {

			Log.erro(NOME_CLASSE, METODO_SALVAR, e);

		}finally{

			fecharConexao();

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

		Comunidade comunidade = (Comunidade) objeto; 

		String update = "update comunidade" + 
				" set nome = ?, usuario_lider = ?" + 
				" where id = ?";
		try {

			preparedStatement = conexao.prepareStatement(update);
			preparedStatement.setString(1, comunidade.getNome());
			preparedStatement.setInt(2, comunidade.getUsarioLider());
			preparedStatement.setInt(3, comunidade.getId());

			status = preparedStatement.executeUpdate();

			Log.sucesso(NOME_CLASSE, METODO_ATUALIZAR);

		} catch (Exception e) {
			Log.erro(NOME_CLASSE, METODO_ATUALIZAR, e);
		} finally{
			fecharConexao();
		}

		return status;
	}

	@Override
	public int atualizar(Object objeto, Map<String, Object> campos) {
		// TODO
		return 0;
	}

	@Override
	public int excluir(Object objeto) {

		int status = 0;

		abrirConexao();

		Comunidade comunidade = (Comunidade) objeto;

		String delete = "delete from comunidade where id = ?";

		try {

			preparedStatement = conexao.prepareStatement(delete);

			preparedStatement.setInt(1, comunidade.getId());

			status = preparedStatement.executeUpdate();

			Log.sucesso(NOME_CLASSE, METODO_EXCLUIR);

		} catch (Exception e) {

			Log.erro(NOME_CLASSE, METODO_EXCLUIR, e);

		}

		return status;

	}

	public Integer buscarQuantidadeDeMembros(Integer idComunidade){
		abrirConexao();

		Integer qtd = null;

		String select = "select count(*) as qtd from usuario u"
				+ " inner join usuario_comunidade uc"
				+ " on u.id = uc.id_usuario"
				+ " inner join comunidade c "
				+ " on uc.id_comunidade = c.id"
				+ " where c.id = ?";
		try {

			preparedStatement = conexao.prepareStatement(select);

			preparedStatement.setInt(1, idComunidade);

			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				qtd = resultSet.getInt("qtd");
			}

			Log.sucesso(NOME_CLASSE, METODO_BUSCAR_QTD_MEMBROS);

		} catch (SQLException e) {
			Log.erro(NOME_CLASSE, METODO_BUSCAR_QTD_MEMBROS, e);
		} finally{
			fecharConexao();
		}
		return qtd;
	}

	public Integer buscarTotalPontosDosMembros(Integer idComunidade){
		abrirConexao();

		Integer pontos = null;

		String select = "select sum(u.pontuacao) as pontos from usuario u"
				+ " inner join usuario_comunidade uc"
				+ " on u.id = uc.id_usuario"
				+ " inner join comunidade c "
				+ " on uc.id_comunidade = c.id"
				+ " where c.id = ?";
		try {

			preparedStatement = conexao.prepareStatement(select);

			preparedStatement.setInt(1, idComunidade);

			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				
				pontos = resultSet.getInt("pontos");
			}

			Log.sucesso(NOME_CLASSE, METODO_BUSCAR_TOTAL_PONTOS);

		} catch (SQLException e) {
			Log.erro(NOME_CLASSE, METODO_BUSCAR_TOTAL_PONTOS, e);
		} finally{
			fecharConexao();
		}
		return pontos;
	}
	
	public String buscarNomeLider(Integer idComunidade){
		
		abrirConexao();
		
		String nomeLider = null;
		
		String select = "select concat(u.nome, concat(' ', u.sobrenome)) as nome from usuario u" + 
				" where id = (select c.usuario_lider from comunidade c where c.id = ?)";
		try {
			
			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, idComunidade);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				nomeLider = resultSet.getString("nome");
			}
			
			Log.sucesso(NOME_CLASSE, METODO_BUSCAR_NOME_LIDER);
		} catch (SQLException e) {
			Log.erro(NOME_CLASSE, METODO_BUSCAR_NOME_LIDER, e);
		} finally {
			fecharConexao();
		}
		
		return nomeLider;
	}


	/*public static void main(String[] args) {

		ComunidadeDAO dao = new ComunidadeDAO();

		Comunidade c = (Comunidade) dao.buscar(1);

		System.out.println(c.getNome() + "Lider + " + c.getUsarioLider());

		c.setUsarioLider(2);

		dao.atualizar(c);

		c = (Comunidade) dao.buscar(1);

		System.out.println(c.getNome() + "Lider + " + c.getUsarioLider());


	}*/

}
