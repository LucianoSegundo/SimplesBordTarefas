package com.LFSoftware.SimplesBoardTarefas.persistence.entity;

import java.util.stream.Stream;

public enum TipoBoardEnum {

	Inicio, Finalizado, Pendente, Cancelado;

	public static TipoBoardEnum findByName(final String name) {
		return Stream.of(TipoBoardEnum.values()).filter(b -> b.name().equals(name)).findFirst().orElseThrow();
	}
}
