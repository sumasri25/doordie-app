package com.taskflow.repository;

import com.taskflow.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /** All todos ordered newest first */
    List<Todo> findAllByOrderByCreatedAtDesc();

    /** Find by completion status */
    List<Todo> findByCompletedOrderByCreatedAtDesc(boolean completed);

    /** Find by priority */
    List<Todo> findByPriorityOrderByCreatedAtDesc(String priority);
}