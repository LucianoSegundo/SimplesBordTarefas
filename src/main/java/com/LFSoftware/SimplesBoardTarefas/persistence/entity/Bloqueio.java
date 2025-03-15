package com.LFSoftware.SimplesBoardTarefas.persistence.entity;

import java.time.OffsetDateTime;

public class Bloqueio {
	private Long id;
	private boolean bloqueado;
	private OffsetDateTime dataBloqueio;
	private String motivoBloqueio;
	private OffsetDateTime dataDesbloqueio;
	private String motivoDesbloqueio;

	public Bloqueio(String motivoBloqueio) {
		this.bloqueado = true;
		this.dataBloqueio = OffsetDateTime.now();
		this.motivoBloqueio = motivoBloqueio;
	}

	public Bloqueio(Long id, boolean bloqueado, OffsetDateTime dataBloqueio, String motivoBloqueio,
			OffsetDateTime dataDesbloqueio, String motivoDesbloqueio) {
		this.id = id;
		this.bloqueado = bloqueado;
		this.dataBloqueio = dataBloqueio;
		this.motivoBloqueio = motivoBloqueio;
		this.dataDesbloqueio = dataDesbloqueio;
		this.motivoDesbloqueio = motivoDesbloqueio;
	}

	public boolean Desbloquear(String motivo) {
		if (motivo == null || motivo.isBlank())
			return false;
		this.bloqueado = false;
		this.motivoDesbloqueio = motivo;
		this.dataDesbloqueio = OffsetDateTime.now();

		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public OffsetDateTime getDataBloqueio() {
		return dataBloqueio;
	}

	public void setDataBloqueio(OffsetDateTime dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}

	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}

	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}

	public OffsetDateTime getDataDesbloqueio() {
		return dataDesbloqueio;
	}

	public void setDataDesbloqueio(OffsetDateTime dataDesbloqueio) {
		this.dataDesbloqueio = dataDesbloqueio;
	}

	public String getMotivoDesbloqueio() {
		return motivoDesbloqueio;
	}

	public void setMotivoDesbloqueio(String motivoDesbloqueio) {
		this.motivoDesbloqueio = motivoDesbloqueio;
	}
}
