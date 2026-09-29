package com.example.taskapp.repository;

import com.example.taskapp.entity.Task;
import com.example.taskapp.entity.TaskStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByStatus(TaskStatus status);

    @Query("""
        SELECT t
        FROM Task t
        WHERE
            (
                :keyword = ''
                OR LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
            AND (:status IS NULL OR t.status = :status)
            AND (:userId IS NULL OR t.user.id = :userId)
        ORDER BY t.id
        """)
    List<Task> search(
        @Param("keyword") String keyword,
        @Param("status") TaskStatus status,
        @Param("userId") Long userId
    );
}
