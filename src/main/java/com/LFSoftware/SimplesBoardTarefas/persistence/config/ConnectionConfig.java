package com.LFSoftware.SimplesBoardTarefas.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionConfig {


	private static final String URL = "jdbc:mysql://localhost:3306/banco";
	private static final String usuario = "root";
	private static final String senha = "";
	private static Connection conn = null;

	public static Connection getCurrentConnection() throws Exception {
		if (conn == null)
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(URL, usuario, senha);
				conn.setAutoCommit(false);

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
