package com.LFSoftware.SimplesBoardTarefas.ui;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.LFSoftware.SimplesBoardTarefas.exception.EntidadeNaoEncontrada;
import com.LFSoftware.SimplesBoardTarefas.persistence.config.ConnectionConfig;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.Board;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.BoardColumns;
import com.LFSoftware.SimplesBoardTarefas.persistence.entity.TipoBoardEnum;
import com.LFSoftware.SimplesBoardTarefas.service.BoardQueryService;
import com.LFSoftware.SimplesBoardTarefas.service.BoardService;

public class MainMenu {
	private final Scanner entrada = new Scanner(System.in).useDelimiter("\n");
	private final Connection conexao;

	public MainMenu() throws Exception {
		this.conexao = ConnectionConfig.getCurrentConnection();
	}

	public void execute() throws Exception {
		System.out.println("Fala meu garotão, Você gostaria de gerencar alguns boards?, me diz ai;");

		String sim = "sim,bixo, eu quero ";
		String nao = "nao, bixo, valeu, mas eu quero ";
		Boolean priemriaExecucao = true;
		var option = -1;
		while (true) {

			System.out.println("1 - " + sim + "Criar um novo board");
			System.out.println("2 - " + sim + "Selecionar um board existente");
			System.out.println("3 - " + sim + "Excluir um board");
			System.out.println("4 - " + nao + "Sair");

			if (priemriaExecucao == true) {
				priemriaExecucao = false;
				sim = "";
				nao = "";
			}

			option = entrada.nextInt();
			switch (option) {
			case 1 -> createBoard();
			case 2 -> selectBoard();
			case 3 -> deleteBoard();
			case 4 -> System.exit(0);
			default -> System.out.println("me ajuda a te ajudar... me fiz ai uma opção do menu");
			}
		}
	}

	private void createBoard() throws Exception {
		Board board = new Board();
		System.out.println("Bixo, me diz ai o nome do teu board");
		board.setNome(entrada.next());

		System.out.println(
				"O teu board vai ter mais do que 3 colunas? me diz ai, quantas vão ter, se não tiver mais nenhuma só digita 0");
		int colunasAdicionais;
		try {
			colunasAdicionais = entrada.nextInt();
		} catch (Exception e) {
			System.out.println("Isso que você fez é coisa de capivara, sem colunas extras para você");
			colunasAdicionais = 0;
		}

		List<BoardColumns> colunas = new ArrayList<>();

		System.out.println("Bora começar a brincadeira, qual o nome da coluna inicial?");

		String nomeColuna;

		nomeColuna = entrada.next();
		if (nomeColuna == null || nomeColuna.isBlank()) {
			System.out.println("vacilão você, como punição a coluna vai ter o nome padrão");
			nomeColuna = "inicio";
		}
		BoardColumns primeiraColuna = createColumn(nomeColuna, TipoBoardEnum.Inicio, 0);

		colunas.add(primeiraColuna);

		for (int i = 0; i < colunasAdicionais; i++) {
			String mensagem = "da ";
			if (i > 0)
				mensagem = "de mais uma ";

			System.out.println("Me diz ai o nome " + mensagem + "coluna de tarefa pendente");

			nomeColuna = entrada.next();

			if (nomeColuna == null || nomeColuna.isBlank()) {
				System.out.println("vacilão você, como punição a coluna vai ter o nome padrão");

				nomeColuna = "pendente " + i + 1;
			}

			BoardColumns colunaAdicional = createColumn(nomeColuna, TipoBoardEnum.Pendente, i + 1);

			colunas.add(colunaAdicional);

		}

		System.out.println("Quase acabando, o nome da coluna final");

		nomeColuna = entrada.next();

		if (nomeColuna == null || nomeColuna.isBlank()) {
			System.out.println("vacilão você, como punição a coluna vai ter o nome padrão");
			nomeColuna = "finalizado";
		}

		BoardColumns colunaFinal = createColumn(nomeColuna, TipoBoardEnum.Finalizado, colunasAdicionais + 1);

		colunas.add(colunaFinal);

		System.out.println("Pronto bixo, satisfeito? agora pra terminar o nome da coluna de cancelamento do baord");

		nomeColuna = entrada.next();

		if (nomeColuna == null || nomeColuna.isBlank()) {
			System.out.println("Tú é vacição mesmo... Como punição a coluna vai ter o nome padrão");
			nomeColuna = "cancelado";
		}
		var cancelColumn = createColumn(nomeColuna, TipoBoardEnum.Cancelado, colunasAdicionais + 2);

		colunas.add(cancelColumn);

		board.setColunas(colunas);

		try (Connection connection = ConnectionConfig.getCurrentConnection()) {
			BoardService service = new BoardService(connection);
			service.inserir(board);
		}

	}

	private void selectBoard() throws Exception {
		System.out.println("Qual o id do board que tu quer?");
		var id = entrada.nextLong();
		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {

			BoardQueryService service = new BoardQueryService(conexao);
			var optional = service.findById(id);

			try {
				Board b = optional.orElseThrow(() -> new EntidadeNaoEncontrada(
						"Com esse id não tem não tem nenhum board, tem certeza que o id " + id + " tá certo?"));
				new BoardMenu(b).execute();

			} catch (EntidadeNaoEncontrada e) {
				System.out.println(e.getMessage());
			}
			;

		}
	}

	private void deleteBoard() throws Exception {
		System.out.println("Diz ai o id do board que tu quer apagar");
		var id = entrada.nextLong();
		try (Connection conexao = ConnectionConfig.getCurrentConnection()) {
			BoardService service = new BoardService(conexao);
			if (service.delete(id)) {
				System.out.println("Pronto, já demos cabo do board " + id + " foi excluido");
			} else {
				System.out.println("Não tem nenhum board com esse id, cara.");
			}
		}
	}

	private BoardColumns createColumn(String nomeColuna, TipoBoardEnum tipo, int ordem) {
		BoardColumns boardColumn = new BoardColumns();
		boardColumn.setNome(nomeColuna);
		boardColumn.setTipo(tipo);
		boardColumn.setOrdem(ordem);
		return boardColumn;
	}

}
