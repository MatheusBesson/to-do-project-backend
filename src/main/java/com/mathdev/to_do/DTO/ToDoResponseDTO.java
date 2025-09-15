package com.mathdev.to_do.DTO;

import com.mathdev.to_do.model.ToDo;

import java.time.LocalDateTime;

public record ToDoResponseDTO(Long id, String title, String description, boolean status, LocalDateTime creationDate, LocalDateTime lastUpdatedDate) {

    public ToDoResponseDTO(ToDo toDo) {
        this(toDo.getId(), toDo.getTitle(), toDo.getDescription(),toDo.isStatus(), toDo.getCreationDate(), toDo.getLastUpdatedDate());
    }

}
