package com.LFSoftware.SimplesBoardTarefas.DTO;

import java.time.OffsetDateTime;

public record CardeDTO(Long id, String titulo, String descricao, OffsetDateTime dataCriacao, boolean bloqueado,
		OffsetDateTime dataBloqueio, String motivoBloqueio, int quantidadeBloqueio, Long colunaId, String nomeColuna) {
};
