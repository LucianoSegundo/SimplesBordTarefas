package com.LFSoftware.SimplesBoardTarefas.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.LFSoftware.SimplesBoardTarefas.persistence.entity.Board;

public class BordDao {

	private final Connection conexao;

	public BordDao(Connection conexao) {
		this.conexao = conexao;
	}

	public Board Inserir(Board board) throws SQLException {
		String query = "INSERT INTO boards (nome) VALUES(?);";

		try (PreparedStatement pstm = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			pstm.setString(1, board.getNome());
			pstm.executeUpdate();

			ResultSet resultado = pstm.getGeneratedKeys();
			if (resultado.next()) {
				board.setId(resultado.getLong(1));

			}

		} catch (SQLException e) {
			throw e;
		}
		return board;

	}

	public void delete(final Long id) throws SQLException {

		var query = "DELETE FROM boards WHERE id = ?;";

		try (PreparedStatement pstm = conexao.prepareStatement(query)) {
			pstm.setLong(1, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Delesão do board falhou");
			throw e;
		}
	}

	public Optional<Board> findById(final Long id) throws SQLException {
		var sql = "SELECT id, nome FROM boards WHERE id = ?;";

		try (PreparedStatement pstm = conexao.prepareStatement(sql)) {

			pstm.setLong(1, id);
			pstm.executeQuery();

			ResultSet resultSet = pstm.getResultSet();

			if (resultSet.next()) {

				Board board = new Board();

				board.setId(resultSet.getLong("id"));
				board.setNome(resultSet.getString("nome"));

				return Optional.of(board);
			}
			return Optional.empty();
		}
	}

	public boolean existe(final Long id) throws SQLException {

		var sql = "SELECT 1 FROM boards WHERE id = ?;";

		try (PreparedStatement pstm = conexao.prepareStatement(sql)) {

			pstm.setLong(1, id);
			pstm.executeQuery();

			return pstm.getResultSet().next();
		}
	}

}
