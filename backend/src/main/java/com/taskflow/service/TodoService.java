package com.taskflow.service;

import com.taskflow.model.Todo;
import com.taskflow.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    // ─── Get all ─────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Todo> getAll() {
        return repo.findAllByOrderByCreatedAtDesc();
    }

    // ─── Get one ─────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public Todo getById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Todo not found: " + id));
    }

    // ─── Create ──────────────────────────────────────────────────────
    public Todo create(Todo todo) {
        todo.setId(null); // Ensure auto-generated
        return repo.save(todo);
    }

    // ─── Update ──────────────────────────────────────────────────────
    public Todo update(Long id, Todo updated) {
        Todo existing = getById(id);
        existing.setText(updated.getText());
        existing.setCompleted(updated.isCompleted());
        existing.setPriority(updated.getPriority());
        return repo.save(existing);
    }

    // ─── Delete ──────────────────────────────────────────────────────
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Todo not found: " + id);
        }
        repo.deleteById(id);
    }

    // ─── Delete all completed ─────────────────────────────────────────
    public int deleteCompleted() {
        List<Todo> completed = repo.findByCompletedOrderByCreatedAtDesc(true);
        repo.deleteAll(completed);
        return completed.size();
    }
}