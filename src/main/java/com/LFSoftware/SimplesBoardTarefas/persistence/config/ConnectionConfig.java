package com.LFSoftware.SimplesBoardTarefas.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionConfig {



	private static  String URL= System.getenv("DB_URL");
	private static  String usuario = System.getenv("DB_USER");
	private static  String senha =  System.getenv("BD_SENHA");;
	private static Connection conn = null;

	public ConnectionConfig() {}
	public static Connection getCurrentConnection() throws Exception {
		if (conn == null)
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(URL, usuario, senha);
				conn.setAutoCommit(false);
				System.out.println("sucesso");

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
		return conn;
	}

	static Connection getNewConnection() throws SQLException  {
		Connection conexao = DriverManager.getConnection(URL, usuario, senha);
		conexao.setAutoCommit(false);
		return conexao;

	}

}
