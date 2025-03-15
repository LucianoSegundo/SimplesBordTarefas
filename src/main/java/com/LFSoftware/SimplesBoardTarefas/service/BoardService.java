package com.LFSoftware.SimplesBoardTarefas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.LFSoftware.SimplesBoardTarefas.persistence.dao.BoardColumnDao;
import com.LFSoftware.SimplesBoardTarefas.persistence.dao.BordDao;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.Board;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.BoardColumns;

public class BoardService {

	private final Connection conexao;

	public BoardService(Connection conexao) throws Exception {

		this.conexao = conexao;

	}

	public Board inserir(final Board bord) throws SQLException {
		BordDao bordDao = new BordDao(conexao);
		BoardColumnDao boardColumnDao = new BoardColumnDao(conexao);
		try {
			bordDao.Inserir(bord);

			List<BoardColumns> columns = bord.getColunas().stream().map(c -> {
				c.setBord(bord);
				return c;
			}).toList();

			for (var column : columns) {
				boardColumnDao.inserir(column);
			}
			conexao.commit();
			conexao.close();
		} catch (SQLException e) {
			conexao.rollback();
			conexao.close();

			throw e;
		}
		return bord;
	}

	public boolean delete(final Long id) throws SQLException {
		BordDao bordao = new BordDao(conexao);
		try {
			if (bordao.existe(id) == false) {
				return false;
			}

			bordao.delete(id);
			conexao.commit();

			return true;

		} catch (SQLException e) {
			conexao.rollback();
			throw e;
		}
	}

}