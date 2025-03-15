package com.LFSoftware.SimplesBoardTarefas.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

import com.LFSoftware.SimplesBoardTarefas.persistence.ferramentas.conversorData;

public class BloqueioDao {

	private final Connection conexao;

	public BloqueioDao(Connection conexao) {
		this.conexao = conexao;
	}

	public void bloquearCatd(String motivoBloqueio, final long cardeId) throws SQLException {

		String query = "INSERT INTO bloqueios (dataBloqueio, motivoBloqueio, carde_id ) VALUES(?,?,?); ";

		try (PreparedStatement pstm = conexao.prepareStatement(query)) {
			Timestamp agora = conversorData.toTimestamp(OffsetDateTime.now());

			pstm.setTimestamp(1, agora);
			pstm.setString(2, motivoBloqueio);
			pstm.setLong(3, cardeId);

			pstm.execute();

		} catch (SQLException e) {
			throw e;

		}
	}

	public void desBloquearCatd(String motivoDesbloqueio, final long cardeId) throws SQLException {
		String query = "UPDATE bloqueios set  dataDesbloqueio = ?, motivoDesBloqueio =? WHERE carde_id = ? AND motivoDesBloqueio IS NULL;";

		try (PreparedStatement pstm = conexao.prepareStatement(query)) {
			Timestamp agora = conversorData.toTimestamp(OffsetDateTime.now());

			pstm.setTimestamp(1, agora);
			pstm.setString(2, motivoDesbloqueio);
			pstm.setLong(3, cardeId);

			pstm.execute();

		} catch (SQLException e) {
			throw e;
		}

	}
}
