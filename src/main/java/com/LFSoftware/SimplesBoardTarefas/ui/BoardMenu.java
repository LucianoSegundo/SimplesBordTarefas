package com.LFSoftware.SimplesBoardTarefas.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.LFSoftware.SimplesBoardTarefas.DTO.BoardColumnDTO;
import com.LFSoftware.SimplesBoardTarefas.DTO.BoardDTO;
import com.LFSoftware.SimplesBoardTarefas.persistence.config.ConnectionConfig;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.Board;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.BoardColumns;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.Carde;
import com.LFSoftware.SimplesBoardTarefas.service.BoardColumnQueryService;
import com.LFSoftware.SimplesBoardTarefas.service.BoardQueryService;
import com.LFSoftware.SimplesBoardTarefas.service.CardService;

public class BoardMenu {

	private final Connection conexao;
	private final Board board;
	private final Scanner entrada = new Scanner(System.in).useDelimiter("\n");

	BoardMenu(Board board) throws Exception {
		this.board = board;
		this.conexao = ConnectionConfig.getCurrentConnection();
	}

	public void execute() {
		try {
			System.out.println("Você de novo, bem, você está no Board " + board.getNome() + ", oque você quer fazer?");
			int opção = -1;
			while (opção != 9) {
				System.out.println("1 - Criar um card");
				System.out.println("2 - Mover um card");
				System.out.println("3 - Bloquear um card");
				System.out.println("4 - Desbloquear um card");
				System.out.println("5 - Cancelar um card");
				System.out.println("6 - Ver board");
				System.out.println("7 - Ver coluna com cards");
				System.out.println("8 - Ver card");
				System.out.println("9 - Voltar para o menu anterior um card");
				System.out.println("10 - Sair");
				opção = entrada.nextInt();
				switch (opção) {
				case 1 -> criarCard();
				case 2 -> moverCard();
				case 3 -> bloquearCard();
				case 4 -> DesbloquearCard();
				case 5 -> cancelarCard();
				case 6 -> exibirBoard();
				case 7 -> exibirColuna();
				case 8 -> exibirCard();
				case 9 -> System.out.println("Então vamos voltar");
				case 10 -> System.exit(0);
				default -> System.out.println("Tá de sacanagem, informe uma opção do menu");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void exibirBoard() throws Exception {
		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {
			Optional<BoardDTO> optional = new BoardQueryService(conexao).consultarBoardCompleto(board.getId());
			optional.ifPresent(b -> {

				System.out.printf("Board [%s,%s]\n", b.id(), b.nome());
				b.colunas().forEach(c -> System.out.printf("Coluna [%s] tipo: [%s] tem %s cardes\n", c.id(), c.nome(),
						c.quantidade()));
			});
		}
	}

	private void exibirColuna() throws SQLException, Exception {
		List<Long> columnsIds = board.getColunas().stream().map(BoardColumns::getId).toList();
		Long selectedColumnId = -1L;

		while (!columnsIds.contains(selectedColumnId)) {

			System.out.printf("Escolha uma coluna do board %s pelo id\n", board.getNome());

			board.getColunas().forEach(c -> System.out.printf("%s - %s [%s]\n", c.getId(), c.getNome(), c.getTipo()));

			selectedColumnId = entrada.nextLong();
		}

		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {
			Optional<BoardColumns> column = new BoardColumnQueryService(conexao).consultarColuna(selectedColumnId);

			column.ifPresent(co -> {

				System.out.printf("Coluna %s tipo %s\n", co.getNome(), co.getTipo());

				co.getCardes().forEach(ca -> System.out.printf("Card %s - Titulo: %s\n Descrição: %s \n", ca.getId(),
						ca.getTitulo(), ca.getDescricao()));
			});
		}
	}

	private void exibirCard() throws Exception {
		System.out.println("Informe o id do card que deseja visualizar");
		var selectedCardId = entrada.nextLong();
		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {
			new CardService(conexao).consultarCard(selectedCardId).ifPresentOrElse(c -> {
				System.out.printf("Card %s - %s.\n", c.id(), c.titulo());
				System.out.printf("Descrição: %s\n", c.descricao());
				System.out.println(
						c.bloqueado() ? "Está bloqueado. Motivo: " + c.motivoBloqueio() : "Não está bloqueado");
				System.out.printf("Já foi bloqueado %s vezes\n", c.quantidadeBloqueio());
				System.out.printf("Está no momento na coluna %s - %s\n", c.colunaId(), c.nomeColuna());
			}, () -> System.out.printf("Não existe um card com o id %s\n", selectedCardId));
		}
	}

	private void criarCard() throws SQLException, Exception {
		Carde carde = new Carde();

		System.out.println("Qual o título do card");
		String texto = entrada.next();
		carde.setTitulo(texto);
		System.out.println("Como é a descrição do card");
		String texto2 = entrada.next();

		carde.setDescricao(texto2);

		carde.setColuna(board.getColunaInicial());

		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {

			new CardService(conexao).criar(carde);
		}

	}

	private void moverCard() throws Exception {

		System.out.println("Informe o id do card que deseja mover para a próxima coluna");
		Long cardId = entrada.nextLong();

		List<BoardColumnDTO> boardColumnDto = board.getColunas().stream()
				.map(bc -> new BoardColumnDTO(bc.getId(), bc.getTipo(), bc.getOrdem())).toList();

		try (Connection connection = ConnectionConfig.getCurrentConnection()) {

			new CardService(connection).moverCard(cardId, boardColumnDto);

		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void bloquearCard() throws SQLException, Exception {

		System.out.println("Informe o id do card que será bloqueado");
		Long cardId = entrada.nextLong();

		System.out.println("Informe o motivo do bloqueio do card");
		String motivoBloqueio = entrada.next();
		List<BoardColumnDTO> boardColumnDto = board.getColunas().stream()
				.map(bc -> new BoardColumnDTO(bc.getId(), bc.getTipo(), bc.getOrdem())).toList();

		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {

			new CardService(conexao).bloquearCard(cardId, motivoBloqueio, boardColumnDto);

		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void DesbloquearCard() throws Exception {
		System.out.println("Informe o id do card que será desbloqueado");
		Long cardId = entrada.nextLong();

		System.out.println("Informe o motivo do desbloqueio do card");
		String motivoDesbloqueio = entrada.next();

		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {

			new CardService(conexao).desbloquearCard(cardId, motivoDesbloqueio);

		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void cancelarCard() throws SQLException, Exception {
		System.out.println("Informe o id do card que deseja mover para a coluna de cancelamento");

		Long cardId = entrada.nextLong();

		BoardColumns colunaCancelada = board.getColunaCanceladas();

		List<BoardColumnDTO> boardColumnDto = board.getColunas().stream()
				.map(bc -> new BoardColumnDTO(bc.getId(), bc.getTipo(), bc.getOrdem())).toList();

		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {

			new CardService(conexao).cancelarCard(cardId, colunaCancelada.getId(), boardColumnDto);

		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

}
