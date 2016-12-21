package com.greeningu.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.greeningu.bean.Voto;
import com.greeningu.conexao.Conexao;
import com.greeningu.fabricadeobjetos.ConstrutorDeObjetos;
import com.greeningu.log.Log;

public class VotoDAO extends Dao implements CRUD{
	
	private static final String NOME_CLASSE = "VotoDAO";
	private static final String METODO_JA_VOTADO = "jaVotado()";

	public VotoDAO(){}

	@SuppressWarnings("unused")
	@Override
	public Object buscar(Integer id) {
		return null;
	}


	public Object buscar(Integer idUsuarioVotador, Integer idPostagem){

		abrirConexao();

		Voto voto = null;
		String select = "select * from voto where id_usuario_votador = ? and id_postagem = ?";
		try {

			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, idUsuarioVotador);
			preparedStatement.setInt(2, idPostagem);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				voto = ConstrutorDeObjetos.construirVoto(resultSet, new Voto());
			}
			
			Log.sucesso(NOME_CLASSE, METODO_BUSCAR);
			

		} catch (SQLException e) {
			Log.erro(NOME_CLASSE, METODO_BUSCAR, e);
		}finally{
			
			fecharConexao();
		}
		return voto;
	}

	@Override
	public List listar() {
		abrirConexao();

		Voto voto = null;
		List<Voto> votos = new ArrayList<Voto>();

		String select = "select * from voto";

		try {
			preparedStatement = conexao.prepareStatement(select);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()){
				voto = new Voto();
				voto.setData(resultSet.getDate("data_voto"));
				voto.setIdPostagem(resultSet.getInt("id_postagem"));
				voto.setIdUsuarioVotador(resultSet.getInt("id_usuario_votador"));
				voto.setPontos(resultSet.getInt("pontos"));
				votos.add(voto);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			fecharConexao();
		}
		return votos;
	}

	@Override
	public int salvar(Object objeto) {
		
		int status = 0;
		
		abrirConexao();
		Voto votoTemp = (Voto) objeto;
		
		String insert = "insert into voto(data_voto, id_usuario_votador, id_postagem, pontos)"
				+ " values(sysdate(), ?,?,?)";
		try {
			preparedStatement = conexao.prepareStatement(insert);
			preparedStatement.setInt(1,votoTemp.getIdUsuarioVotador());
			preparedStatement.setInt(2, votoTemp.getIdPostagem());
			preparedStatement.setInt(3, votoTemp.getPontos());
			
			status = preparedStatement.executeUpdate();
			
			Log.sucesso(NOME_CLASSE, METODO_SALVAR);
			
		} catch (SQLException e) {
			Log.erro(NOME_CLASSE, METODO_SALVAR, e);
		}finally{
			
			fecharConexao();
		}
		
		return status;
	}
	
	public Boolean jaVotado(Integer idUsuario, Integer idPostagem){
		
		abrirConexao();
		
		boolean status = false;
		
		String select = "select * from voto v"
				+ " where v.id_usuario_votador = ?"
				+ " and v.id_postagem = ?";
		try {
			preparedStatement = conexao.prepareStatement(select);
			preparedStatement.setInt(1, idUsuario);
			preparedStatement.setInt(2, idPostagem);
			
			resultSet = preparedStatement.executeQuery();
			
			status = resultSet.next();
			
			Log.sucesso(NOME_CLASSE, METODO_JA_VOTADO);
		} catch (SQLException e) {
			Log.erro(NOME_CLASSE, METODO_JA_VOTADO, e);
		} finally {
			fecharConexao();
		}
		
		return status;
		
	}
	
	@SuppressWarnings("unused")
	@Override
	public int salvar(Object objeto, Map campos) {
		// TODO
		return 0;
	}
	

	@Override
	public int atualizar(Object objeto, Map campos) {
		// TODO
		return 0;
		
	}
	
	@SuppressWarnings("unused")
	@Override
	public int atualizar(Object objeto) {
		// TODO
		return 0;
	}

	@Override
	public int excluir(Object objeto) {
		// TODO Auto-generated method stub
		return 0;
	}

}
