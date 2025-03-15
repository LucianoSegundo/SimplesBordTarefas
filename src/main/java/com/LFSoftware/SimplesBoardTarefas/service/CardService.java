package com.LFSoftware.SimplesBoardTarefas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.LFSoftware.SimplesBoardTarefas.DTO.BoardColumnDTO;
import com.LFSoftware.SimplesBoardTarefas.DTO.BoardDTO;
import com.LFSoftware.SimplesBoardTarefas.DTO.CardeDTO;
import com.LFSoftware.SimplesBoardTarefas.exception.CardBloqueadoException;
import com.LFSoftware.SimplesBoardTarefas.exception.CardFinalizadoException;
import com.LFSoftware.SimplesBoardTarefas.exception.EntidadeNaoEncontrada;
import com.LFSoftware.SimplesBoardTarefas.persistence.config.ConnectionConfig;
import com.LFSoftware.SimplesBoardTarefas.persistence.dao.BloqueioDao;
import com.LFSoftware.SimplesBoardTarefas.persistence.dao.CardeDao;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.Carde;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.TipoBoardEnum;

public class CardService {

	private final Connection conexao;

	public CardService(Connection conexao) {
		this.conexao = conexao;
	}

	public Carde criar(Carde carde) throws SQLException {
		try {

			CardeDao BancoCard = new CardeDao(conexao);

			BancoCard.inserir(carde);

			conexao.commit();

		} catch (SQLException e) {
			conexao.rollback();
			throw e;
		}
		return carde;
	}

	public void moverCard(final Long cardId, final List<BoardColumnDTO> colunasDTO) throws SQLException {
		try {

			CardeDao bancoCard = new CardeDao(conexao);

			Optional<CardeDTO> optional = bancoCard.findById(cardId);

			CardeDTO dto = optional.orElseThrow(() -> new EntidadeNaoEncontrada("Cara, esse card não existe"));

			if (dto.bloqueado()) {
				var messagem = "bixo, o card " + cardId + " tá bloqueado, tú precisa desbloquea ele se quiser mover";
				throw new CardBloqueadoException(messagem);
			}

			BoardColumnDTO boarAtual = colunasDTO.stream().filter(bc -> bc.id().equals(dto.colunaId())).findFirst()
					.orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));

			if (boarAtual.tipo().equals(TipoBoardEnum.Finalizado)) {
				throw new CardFinalizadoException("O card já está finalizado");
			}
			BoardColumnDTO proximoBoard = colunasDTO.stream().filter(bc -> bc.ordem() == boarAtual.ordem() + 1)
					.findFirst().orElseThrow(() -> new IllegalStateException("O card já foi cancelado"));

			bancoCard.moverCard(proximoBoard.id(), cardId);

			conexao.commit();
			conexao.close();

		} catch (SQLException e) {
			conexao.rollback();
			conexao.close();

			throw e;
		}
	}

	public void cancelarCard(final Long cardId, final Long colunaCancelarId, final List<BoardColumnDTO> colunasDTO)
			throws SQLException {
		try {

			CardeDao bancoCard = new CardeDao(conexao);
			Optional<CardeDTO> optional = bancoCard.findById(cardId);

			CardeDTO dto = optional.orElseThrow(() -> new EntidadeNaoEncontrada("Cara, esse card não existe"));

			if (dto.bloqueado()) {
				var messagem = "bixo, o card " + cardId + " tá bloqueado, tú precisa desbloquea ele se quiser cancelar";
				throw new CardBloqueadoException(messagem);
			}

			BoardColumnDTO boardAtual = colunasDTO.stream().filter(bc -> bc.id().equals(dto.colunaId())).findFirst()
					.orElseThrow(() -> new IllegalStateException(
							"O card informado pertence a outro board, ai tu me quebra"));

			if (boardAtual.tipo().equals(TipoBoardEnum.Finalizado)) {
				throw new CardFinalizadoException("O card já foi finalizado, quer cancelar ele para onde?");
			}

			colunasDTO.stream().filter(bc -> bc.ordem() == boardAtual.ordem() + 1).findFirst()
					.orElseThrow(() -> new IllegalStateException("O card está cancelado, ai você não se ajuda"));

			bancoCard.moverCard(colunaCancelarId, cardId);

			conexao.commit();
			conexao.close();
		} catch (SQLException e) {
			conexao.rollback();
			conexao.close();

			throw e;
		}
	}

	public void bloquearCard(final Long id, final String motivoBloqueio, final List<BoardColumnDTO> colunasDTO)
			throws SQLException {
		try {

			CardeDao bancoCard = new CardeDao(conexao);
			Optional<CardeDTO> optional = bancoCard.findById(id);

			CardeDTO dto = optional.orElseThrow(() -> new EntidadeNaoEncontrada("Cara, esse card não existe"));

			if (dto.bloqueado()) {
				var messagem = "bixo, o card " + id + " tá bloqueado, como tu quer bloquear ele de novo?";
				throw new CardBloqueadoException(messagem);
			}

			BoardColumnDTO boardAtual = colunasDTO.stream().filter(bc -> bc.id().equals(dto.colunaId())).findFirst()
					.orElseThrow(() -> new IllegalStateException(
							"O card informado pertence a outro board, ai tu me quebra"));

			if (boardAtual.tipo().equals(TipoBoardEnum.Finalizado)
					|| boardAtual.tipo().equals(TipoBoardEnum.Cancelado)) {
				var messagem = "não foi dessa vez, o card está " + boardAtual.tipo() + ", não pode ser bloqueado";
				throw new IllegalStateException(messagem);
			}

			BloqueioDao bancoBloqueio = new BloqueioDao(conexao);
			bancoBloqueio.bloquearCatd(motivoBloqueio, id);

			conexao.commit();
			conexao.close();

		} catch (SQLException e) {
			conexao.rollback();
			conexao.close();
			throw e;
		}
	}

	public void desbloquearCard(final Long id, final String motivoDesbloqueio) throws SQLException {
		try {

			CardeDao bancoCard = new CardeDao(conexao);
			Optional<CardeDTO> optional = bancoCard.findById(id);

			CardeDTO dto = optional.orElseThrow(() -> new EntidadeNaoEncontrada("Cara, esse card não existe"));

			if (!dto.bloqueado()) {
				var messagem = "bixo, o card " + id + " não tá bloqueado, como tu quer desbloquear ele?";
				throw new CardBloqueadoException(messagem);
			}

			var bancoDao = new BloqueioDao(conexao);
			bancoDao.desBloquearCatd(motivoDesbloqueio, id);

			conexao.commit();
			conexao.close();
		} catch (SQLException e) {
			conexao.rollback();
			conexao.close();
			throw e;
		}
	}

	public Optional<CardeDTO> consultarCard(long cardId) throws SQLException {

		CardeDao bancoCarde = new CardeDao(conexao);

		Optional<CardeDTO> dto = bancoCarde.findById(cardId);
		return dto;
	}

}
