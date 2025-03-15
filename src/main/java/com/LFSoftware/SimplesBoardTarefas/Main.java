package com.LFSoftware.SimplesBoardTarefas;

import java.sql.Connection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.LFSoftware.SimplesBoardTarefas.persistence.config.ConnectionConfig;
import com.LFSoftware.SimplesBoardTarefas.persistence.migration.EstrategiaMigracao;
import com.LFSoftware.SimplesBoardTarefas.ui.MainMenu;

@Component
public class Main implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		;

		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {
			new EstrategiaMigracao(conexao).executeMigration();
		}

		new MainMenu().execute();

	}

}
