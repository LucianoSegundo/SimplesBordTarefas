package com.LFSoftware.SimplesBoardTarefas.persistence.entity;

import java.util.ArrayList;
import java.util.List;

public class BoardColumn {
 private Long id;
 private String nome;
 private String tipo;
 private int ordem;
 private List<Carde> cardes;

 public BoardColumn() {
	 this.cardes = new ArrayList<Carde>();
 }

 public BoardColumn(Long id, String nome, String tipo, int ordem, List<Carde> cardes) {
     this.id = id;
     this.nome = nome;
     this.tipo = tipo;
     this.ordem = ordem;
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

 public String getTipo() {
     return tipo;
 }

 public void setTipo(String tipo) {
     this.tipo = tipo;
 }

 public int getOrdem() {
     return ordem;
 }

 public void setOrdem(int ordem) {
     this.ordem = ordem;
 }

public List<Carde> getCardes() {
	return cardes;
}

public void setCardes(List<Carde> cardes) {
	this.cardes = cardes;
}
}

