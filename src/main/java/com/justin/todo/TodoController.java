package com.justin.todo;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    @GetMapping
    public List<Todo> getAll() {
        return todoService.getAllTodos(getCurrentUser());
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return todoService.createTodo(todo, getCurrentUser());
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable Long id, @RequestBody Todo updated) {
        return todoService.updateTodo(id, updated, getCurrentUser());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}