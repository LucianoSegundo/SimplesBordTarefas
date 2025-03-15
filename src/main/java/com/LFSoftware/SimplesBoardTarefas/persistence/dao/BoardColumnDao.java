package com.LFSoftware.SimplesBoardTarefas.persistence.dao;

import static java.util.Objects.isNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.LFSoftware.SimplesBoardTarefas.DTO.BoardColumnDTO2;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.BoardColumns;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.Carde;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.TipoBoardEnum;

public class BoardColumnDao {

	private final Connection conexao;

	public BoardColumnDao(Connection conexao) {

		this.conexao = conexao;
	}

	public BoardColumns inserir(BoardColumns coluna) throws SQLException {
		String query = "INSERT INTO board_columns (nome, ordem, tipo, board_id) VALUES (?, ?, ?, ?);";
		try (PreparedStatement pstm = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			pstm.setString(1, coluna.getNome());
			pstm.setInt(2, coluna.getOrdem());
			pstm.setString(3, coluna.getTipo().name());
			pstm.setLong(4, coluna.getBord().getId());
			pstm.executeUpdate();
			ResultSet resultado = pstm.getGeneratedKeys();
			if (resultado.next()) {
				coluna.setId(resultado.getLong(1));

			}
		}

		return coluna;
	}

	public List<BoardColumns> findByBoardId(final Long boardId) throws SQLException {

		List<BoardColumns> boardColunas = new ArrayList<>();
		var query = "SELECT id,nome, ordem, tipo FROM board_columns WHERE board_id = ? ORDER BY ordem";

		try (PreparedStatement pstm = conexao.prepareStatement(query)) {

			pstm.setLong(1, boardId);
			pstm.executeQuery();

			ResultSet resultSet = pstm.getResultSet();

			while (resultSet.next()) {
				BoardColumns colunas = new BoardColumns();
				colunas.setId(resultSet.getLong("id"));
				colunas.setNome(resultSet.getString("nome"));
				colunas.setOrdem(resultSet.getInt("ordem"));
				colunas.setTipo(TipoBoardEnum.findByName(resultSet.getString("tipo")));

				boardColunas.add(colunas);
			}
			return boardColunas;
		}
	}

	public List<BoardColumnDTO2> consultarTodas(Long id) throws SQLException {
		List<BoardColumnDTO2> dtos = new ArrayList<>();
		var sql = """
				SELECT bc.id,
				       bc.nome,
				       bc.tipo,
				       (SELECT COUNT(c.id)
				               FROM cardes c
				              WHERE c.board_column_id = bc.id) quantidade
				  FROM board_columns bc
				 WHERE board_id = ?
				 ORDER BY ordem;
				""";
		try (PreparedStatement pstm = conexao.prepareStatement(sql)) {

			pstm.setLong(1, id);
			pstm.executeQuery();
			ResultSet resultSet = pstm.getResultSet();
			while (resultSet.next()) {
				BoardColumnDTO2 dto = new BoardColumnDTO2(resultSet.getLong("bc.id"), resultSet.getString("bc.nome"),
						TipoBoardEnum.findByName(resultSet.getString("bc.tipo")), resultSet.getInt("quantidade"));
				dtos.add(dto);
			}
			return dtos;
		}
	}

	public Optional<BoardColumns> findById(final Long id) throws SQLException {
		var sql = """
				SELECT bc.nome,
				       bc.tipo,
				       c.id,
				       c.titulo,
				       c.descricao
				  FROM board_columns bc
				  LEFT JOIN cardes c
				    ON c.board_column_id = bc.id
				 WHERE bc.id = ?;
				""";
		try (var statement = conexao.prepareStatement(sql)) {
			statement.setLong(1, id);
			statement.executeQuery();
			var resultSet = statement.getResultSet();

			if (resultSet.next()) {

				BoardColumns colum = new BoardColumns();

				colum.setNome(resultSet.getString("bc.nome"));
				colum.setTipo(TipoBoardEnum.findByName(resultSet.getString("bc.tipo")));

				do {
					Carde card = new Carde();
					if (isNull(resultSet.getString("c.titulo"))) {
						break;
					}
					card.setId(resultSet.getLong("c.id"));
					card.setTitulo(resultSet.getString("c.titulo"));
					card.setDescricao(resultSet.getString("c.descricao"));

					List<Carde> cardes = colum.getCardes();
					cardes.add(card);
					colum.setCardes(cardes);

				} while (resultSet.next());
				return Optional.of(colum);
			}
			return Optional.empty();
		}
	}

}
