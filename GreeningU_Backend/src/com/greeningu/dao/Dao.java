package com.greeningu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.greeningu.conexao.Conexao;
import com.greeningu.log.Log;

public abstract class Dao {
	
	private static final String NOME_CLASSE = "Dao";
	private static final String METODO_ABRIR_CONEXAO = "abrirConexao()";
	private static final String METODO_FECHAR_CONEXAO = "fecharConexao()";
	protected Connection conexao;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;
	protected String insert;
	protected String update;
	protected String delete;
	protected String select;
	
	protected void abrirConexao(){
		try{
			conexao = Conexao.geraConexao();
			Log.sucesso(NOME_CLASSE, METODO_ABRIR_CONEXAO);
		}catch(Exception e){
			Log.erro(NOME_CLASSE, METODO_ABRIR_CONEXAO, e);
		}
	}
	
	protected void fecharConexao(){
		try {
			conexao.close();
			resultSet.close();
			preparedStatement.close();
			Log.sucesso(NOME_CLASSE, METODO_FECHAR_CONEXAO);
		} catch (Exception e) {
			Log.erro(NOME_CLASSE, METODO_FECHAR_CONEXAO, e);
		}
	}
	
}
