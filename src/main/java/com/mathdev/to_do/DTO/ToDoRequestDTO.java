package com.mathdev.to_do.DTO;

import java.time.LocalDateTime;

public record ToDoRequestDTO(String title, String description, boolean status, LocalDateTime creationDate, LocalDateTime lastUpdatedDate) {

}
