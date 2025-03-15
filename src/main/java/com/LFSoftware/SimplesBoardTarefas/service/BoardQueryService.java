package com.LFSoftware.SimplesBoardTarefas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.LFSoftware.SimplesBoardTarefas.DTO.BoardColumnDTO2;
import com.LFSoftware.SimplesBoardTarefas.DTO.BoardDTO;
import com.LFSoftware.SimplesBoardTarefas.persistence.dao.BoardColumnDao;
import com.LFSoftware.SimplesBoardTarefas.persistence.dao.BordDao;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.Board;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.BoardColumns;

public class BoardQueryService {

	private final Connection conexao;

	public BoardQueryService(Connection conexao) {
		this.conexao = conexao;

	}

	public Optional<Board> findById(final Long id) throws SQLException {

		BordDao boardDao = new BordDao(conexao);
		BoardColumnDao boardColumnDao = new BoardColumnDao(conexao);

		Optional<Board> optional = boardDao.findById(id);

		if (optional.isPresent()) {

			Board board = optional.get();
			List<BoardColumns> colunas = boardColumnDao.findByBoardId(id);

			board.setColunas(colunas);

			return Optional.of(board);
		}
		return Optional.empty();
	}

	public Optional<BoardDTO> consultarBoardCompleto(final Long id) throws SQLException {

		BordDao boardDao = new BordDao(conexao);
		BoardColumnDao boardColumnDAO = new BoardColumnDao(conexao);

		Optional<Board> optional = boardDao.findById(id);

		if (optional.isPresent()) {
			Board board = optional.get();

			List<BoardColumnDTO2> columns = boardColumnDAO.consultarTodas(board.getId());

			BoardDTO dto = new BoardDTO(board.getId(), board.getNome(), columns);
			System.out.println();
			return Optional.of(dto);
		}
		return Optional.empty();
	}

}
