package com.greeningu.conexao;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.greeningu.log.Log;

public class Conexao {
	
	private static final String METODO_GERA_CONEXAO = "geraConexao()";
	private static final String NOME_CLASSE = "Conexao";
	
	public static Connection geraConexao() {
		Connection conexao = null;
		Properties props = new Properties();
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream("C:\\greeningu.properties");
			props.load(fis);
			
			Class.forName(props.getProperty("class"));
			
			conexao = DriverManager.getConnection(
					props.getProperty("url"), 
					props.getProperty("username"),
					props.getProperty("password")
			);
			Log.sucesso(NOME_CLASSE, METODO_GERA_CONEXAO);
			
		} catch (IOException | ClassNotFoundException | SQLException e) {
			Log.erro(NOME_CLASSE, METODO_GERA_CONEXAO, e);
		}
		
		return conexao;
	}
}
