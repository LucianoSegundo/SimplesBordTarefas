package com.LFSoftware.SimplesBoardTarefas.persistence.migration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import com.LFSoftware.SimplesBoardTarefas.persistence.config.ConnectionConfig;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public class EstrategiaMigracao {

	private final Connection conexao;

	public EstrategiaMigracao(Connection conexao) {
		this.conexao = conexao;
	}

	public void executeMigration() {

		PrintStream saida = System.out;
		PrintStream saidaErro = System.err;

		try (FileOutputStream fos = new FileOutputStream("liquibase.log")) {

			System.setOut(new PrintStream(fos));
			System.setErr(new PrintStream(fos));

			// inicio do tratamento do liquibase
			try (JdbcConnection jdbcConnection = new JdbcConnection(conexao);) {

				Liquibase liquibase = new Liquibase("/db/changelog/db.changelog-master.yml",
						new ClassLoaderResourceAccessor(), jdbcConnection);
				liquibase.update();

			} catch (LiquibaseException e) {
				e.printStackTrace();
				System.setErr(saidaErro);
			}

			// termino do tratamento do liquibase

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			System.setOut(saida);
			System.setErr(saidaErro);
		}
	}

}
