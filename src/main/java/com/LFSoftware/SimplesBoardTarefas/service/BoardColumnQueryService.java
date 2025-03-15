package com.LFSoftware.SimplesBoardTarefas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.LFSoftware.SimplesBoardTarefas.persistence.dao.BoardColumnDao;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.BoardColumns;

public class BoardColumnQueryService {

	private final Connection conexao;

	public BoardColumnQueryService(Connection conexao) {
		this.conexao = conexao;
	}

	public Optional<BoardColumns> consultarColuna(Long id) throws SQLException {
		BoardColumnDao dao = new BoardColumnDao(conexao);
		return dao.findById(id);

	}

}
