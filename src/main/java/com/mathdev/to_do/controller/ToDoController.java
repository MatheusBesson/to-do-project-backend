package com.mathdev.to_do.controller;


import com.mathdev.to_do.DTO.StatusDTO;
import com.mathdev.to_do.DTO.ToDoRequestDTO;
import com.mathdev.to_do.DTO.ToDoResponseDTO;
import com.mathdev.to_do.model.ToDo;
import com.mathdev.to_do.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/todos") // -------- finish endpoint
@CrossOrigin(origins = "*") // change this for frontend connection
public class ToDoController {

    @Autowired
    private ToDoRepository repository;

    @GetMapping
    public List<ToDoResponseDTO> findAll() {
        List<ToDoResponseDTO> toDoList = repository.findAll().stream().map(ToDoResponseDTO::new).toList();
        return toDoList;
    }

    @PostMapping
    public ToDoRequestDTO saveToDo(@RequestBody ToDoRequestDTO toDoPostData) {
        ToDo todo = new ToDo(toDoPostData);
        repository.save(todo);

        return new ToDoRequestDTO(todo.getTitle(), todo.getDescription(), todo.isStatus(), todo.getCreationDate(),todo.getLastUpdatedDate());
    }

    @PatchMapping("/{id}")
    public ToDoResponseDTO updateToDo(@RequestBody ToDoRequestDTO updateToDo, @PathVariable Long id) {
        LocalDateTime newDate = updateToDo.creationDate();

        // checking if this object already exists in DB
        ToDo todo = repository.findById(id).orElseThrow(() -> new NullPointerException("This task doesn't exists"));

        if(updateToDo.title() != null && !updateToDo.title().isBlank()) {
            todo.setTitle(updateToDo.title());
        }
        if(updateToDo.description() != null && !updateToDo.description().isBlank()) {
            todo.setDescription(updateToDo.description());
        }

       // todo.setStatus(updateToDo.status());
        todo.setLastUpdatedDate(newDate);

        repository.save(todo);

        return new ToDoResponseDTO(todo.getId(), todo.getTitle(), todo.getDescription(), todo.isStatus(), todo.getCreationDate(),todo.getLastUpdatedDate());
    }

    @PatchMapping("/{id}/status")
    public ToDoResponseDTO updateStatus(@PathVariable Long id) {
        ToDo todo = repository.findById(id).orElseThrow(() -> new NullPointerException("This task doesn't exists"));
        if(todo.isStatus()) {
            todo.setStatus(false);
        } else if(!todo.isStatus()) {
            todo.setStatus(true);
        }
        repository.save(todo);

        return new ToDoResponseDTO(todo.getId(), todo.getTitle(), todo.getDescription(), todo.isStatus(), todo.getCreationDate(),todo.getLastUpdatedDate());
    }



   @DeleteMapping("/{id}")
    public void deleteToDo(@PathVariable Long id) {
        ToDo ToDoForDelete = repository.findById(id)
                .orElseThrow(() -> new NullPointerException("ToDo does not exist."));

        repository.delete(ToDoForDelete);
   }
}
