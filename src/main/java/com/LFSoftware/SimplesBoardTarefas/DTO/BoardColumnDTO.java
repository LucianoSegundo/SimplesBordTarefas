package com.LFSoftware.SimplesBoardTarefas.DTO;

import com.LFSoftware.SimplesBoardTarefas.persistence.entity.TipoBoardEnum;

public record BoardColumnDTO(Long id, TipoBoardEnum tipo, int ordem) {

}