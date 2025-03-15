package com.LFSoftware.SimplesBoardTarefas.persistence.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Carde {
	private Long id;
	private String titulo;
	private String descricao;
	private OffsetDateTime dataCriacao;
	private BoardColumns coluna;
	private List<Bloqueio> bloqueios;

	public Carde() {
		this.dataCriacao = OffsetDateTime.now();
		this.setBloqueios(new ArrayList<Bloqueio>());
	}

	public Carde(Long id, String titulo, String descricao, OffsetDateTime dataCriacao, List<Bloqueio> bloqueios,
			BoardColumns coluna) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataCriacao = dataCriacao;
		this.setBloqueios(bloqueios);
		this.setColuna(coluna);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public OffsetDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(OffsetDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<Bloqueio> getBloqueios() {
		return bloqueios;
	}

	public void setBloqueios(List<Bloqueio> bloqueios) {
		this.bloqueios = bloqueios;
	}

	public BoardColumns getColuna() {
		return coluna;
	}

	public void setColuna(BoardColumns coluna) {
		this.coluna = coluna;
	}

}