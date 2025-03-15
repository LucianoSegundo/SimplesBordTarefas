package com.LFSoftware.SimplesBoardTarefas.DTO;

import java.util.List;

import com.LFSoftware.SimplesBoardTarefas.persistence.entity.BoardColumns;

public record BoardDTO(Long id, String nome, List<BoardColumnDTO2> colunas) {

}
