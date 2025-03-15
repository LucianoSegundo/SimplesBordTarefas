package com.LFSoftware.SimplesBoardTarefas.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {
	private Long id;
	private String nome;
	private List<BoardColumns> colunas;

	public Board() {
		this.setColunas(new ArrayList<BoardColumns>());
	}

	public Board(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public BoardColumns getColunaInicial() {
		return filtarColunas(coluna -> coluna.getTipo().equals(TipoBoardEnum.Inicio));
	}

	public BoardColumns getColunaCanceladas() {
		return filtarColunas(coluna -> coluna.getTipo().equals(TipoBoardEnum.Cancelado));
	}

	private BoardColumns filtarColunas(Predicate<BoardColumns> filter) {
		return colunas.stream().filter(filter).findFirst().orElseThrow();
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<BoardColumns> getColunas() {
		return colunas;
	}

	public void setColunas(List<BoardColumns> colunas) {
		this.colunas = colunas;
	}

}
