package com.justin.todo;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public List<Todo> getAllTodos(User user) {
        return repo.findByUser(user);
    }

    public Todo createTodo(Todo todo, User user) {
        todo.setUser(user);
        return repo.save(todo);
    }

    public Todo updateTodo(Long id, Todo updated, User user) {
        updated.setId(id);
        updated.setUser(user);
        return repo.save(updated);
    }

    public void deleteTodo(Long id) {
        repo.deleteById(id);
    }
}