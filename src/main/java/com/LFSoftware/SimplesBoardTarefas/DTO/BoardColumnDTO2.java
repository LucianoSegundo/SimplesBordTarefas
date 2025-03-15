package com.LFSoftware.SimplesBoardTarefas.DTO;

import com.LFSoftware.SimplesBoardTarefas.persistence.entity.TipoBoardEnum;

public record BoardColumnDTO2(Long id, String nome, TipoBoardEnum tipo, int quantidade) {

}