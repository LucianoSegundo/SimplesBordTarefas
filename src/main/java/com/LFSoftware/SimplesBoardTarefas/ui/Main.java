package com.LFSoftware.SimplesBoardTarefas.ui;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.LFSoftware.SimplesBoardTarefas.persistence.config.ConnectionConfig;

@Component
public class Main implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		ConnectionConfig.getCurrentConnection();
	}

}
