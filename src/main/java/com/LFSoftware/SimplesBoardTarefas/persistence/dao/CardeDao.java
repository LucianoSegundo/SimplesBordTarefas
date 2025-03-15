package com.LFSoftware.SimplesBoardTarefas.persistence.dao;

import static java.util.Objects.nonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Optional;

import com.LFSoftware.SimplesBoardTarefas.DTO.CardeDTO;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.Carde;
import com.LFSoftware.SimplesBoardTarefas.persistence.ferramentas.conversorData;

public class CardeDao {

	private Connection conexao;

	public CardeDao(Connection conexao) {
		this.conexao = conexao;
	}

	public Carde inserir(Carde carde) throws SQLException {
		String query = "INSERT INTO cardes (titulo, descricao, board_column_id, dataCriacao) values (?, ?, ?, ?);";
		try (PreparedStatement pstm = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			Timestamp agora = conversorData.toTimestamp(OffsetDateTime.now());

			pstm.setString(1, carde.getTitulo());
			pstm.setString(2, carde.getDescricao());
			pstm.setLong(3, carde.getColuna().getId());
			pstm.setTimestamp(4, agora);
			pstm.executeUpdate();

			ResultSet resultado = pstm.getGeneratedKeys();

			if (resultado.next()) {
				carde.setId(resultado.getLong(1));

			}
		}
		return carde;
	}

	public void moverCard(final Long colunaId, final Long cardId) throws SQLException {
		String query = "UPDATE cardes SET board_column_id = ? WHERE id = ?;";
		try (PreparedStatement pstm = conexao.prepareStatement(query)) {
			pstm.setLong(1, colunaId);
			pstm.setLong(2, cardId);
			pstm.executeUpdate();
		}
	}

	public Optional<CardeDTO> findById(final Long id) throws SQLException {
		var sql = """
				SELECT c.id,
				       c.titulo,
				       c.descricao,
				       c.dataCriacao,
				       b.dataBloqueio,
				       b.motivoBloqueio,
				       c.board_column_id,
				       bc.nome,
				       (SELECT COUNT(sub_b.id)
				               FROM bloqueios sub_b
				              WHERE sub_b.carde_id = c.id) quantidade_bloqueios
				  FROM cardes c
				  LEFT JOIN bloqueios b
				    ON c.id = b.carde_id
				   AND b.dataDesbloqueio IS NULL
				 INNER JOIN board_columns bc
				    ON bc.id = c.board_column_id
				  WHERE c.id = ?;
				""";
		try (PreparedStatement pstm = conexao.prepareStatement(sql)) {
			pstm.setLong(1, id);
			pstm.executeQuery();
			ResultSet resultSet = pstm.getResultSet();
			if (resultSet.next()) {
				CardeDTO dto = new CardeDTO(resultSet.getLong("c.id"), resultSet.getString("c.titulo"),
						resultSet.getString("c.descricao"),
						conversorData.toOffsetDateTime(resultSet.getTimestamp("c.dataCriacao")),
						nonNull(resultSet.getString("b.motivoBloqueio")),
						conversorData.toOffsetDateTime(resultSet.getTimestamp("b.dataBloqueio")),
						resultSet.getString("b.motivoBloqueio"), resultSet.getInt("quantidade_bloqueios"),
						resultSet.getLong("c.board_column_id"), resultSet.getString("bc.nome"));
				return Optional.of(dto);
			}
		}
		return Optional.empty();
	}
}
