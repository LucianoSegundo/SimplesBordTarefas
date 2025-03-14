package com.LFSoftware.SimplesBoardTarefas.persistence.entity;

import java.util.ArrayList;
import java.util.List;

public class Board {
 private Long id;
 private String nome;
 
 private List<BoardColumn> colunas;

 public Board() {
	 this.colunas = new ArrayList<BoardColumn>();

 }

 public Board(Long id, String nome, List<BoardColumn> colunas) {
     this.id = id;
     this.nome = nome;
     this.colunas = colunas;
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

public List<BoardColumn> getColunas() {
	return colunas;
}

public void setColunas(List<BoardColumn> colunas) {
	this.colunas = colunas;
}
}

