package com.LFSoftware.SimplesBoardTarefas.persistence.entity;

import java.util.ArrayList;
import java.util.List;

public class BoardColumns {
	private Long id;
	private String nome;
	private TipoBoardEnum tipo;
	private int ordem;
	private List<Carde> cardes;

	private Board bord;

	public BoardColumns() {
		this.setCardes(new ArrayList<Carde>());
	}

	public BoardColumns(Long id, String nome, TipoBoardEnum tipo, int ordem, Board bord, List<Carde> cardes) {
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.ordem = ordem;
		this.bord = bord;
		this.cardes = cardes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoBoardEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoBoardEnum tipo) {
		this.tipo = tipo;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public Board getBord() {
		return bord;
	}

	public void setBord(Board bord) {
		this.bord = bord;
	}

	public List<Carde> getCardes() {
		return cardes;
	}

	public void setCardes(List<Carde> cardes) {
		this.cardes = cardes;
	}

}
