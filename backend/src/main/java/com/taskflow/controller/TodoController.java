package com.taskflow.controller;

import com.taskflow.model.Todo;
import com.taskflow.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Todo CRUD operations.
 * Base URL: /api/todos
 */
@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")   // Allows the frontend (any port) to call this API
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    /**
     * GET /api/todos
     * Returns all todos, newest first.
     */
    @GetMapping
    public ResponseEntity<List<Todo>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * GET /api/todos/{id}
     * Returns a single todo by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * POST /api/todos
     * Creates a new todo.
     * Body: { "text": "...", "priority": "none|low|med|high" }
     */
    @PostMapping
    public ResponseEntity<Todo> create(@Valid @RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(todo));
    }

    /**
     * PUT /api/todos/{id}
     * Full update of a todo (text, completed, priority).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id,
                                       @Valid @RequestBody Todo todo) {
        return ResponseEntity.ok(service.update(id, todo));
    }

    /**
     * PATCH /api/todos/{id}/toggle
     * Quickly toggle the completed status of a todo.
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggle(@PathVariable Long id) {
        Todo t = service.getById(id);
        t.setCompleted(!t.isCompleted());
        return ResponseEntity.ok(service.update(id, t));
    }

    /**
     * DELETE /api/todos/{id}
     * Deletes a single todo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/todos/completed
     * Deletes all completed todos. Returns count deleted.
     */
    @DeleteMapping("/completed")
    public ResponseEntity<Map<String, Integer>> deleteCompleted() {
        int count = service.deleteCompleted();
        return ResponseEntity.ok(Map.of("deleted", count));
    }
}