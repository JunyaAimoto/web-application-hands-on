package com.example.taskapp.service;

import com.example.taskapp.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.example.taskapp.dto.SummaryResponse;
import com.example.taskapp.entity.TaskStatus;
import com.example.taskapp.dto.TaskResponse;
import com.example.taskapp.exception.NotFoundException;
import com.example.taskapp.entity.Task;
import com.example.taskapp.dto.TaskRequest;
import com.example.taskapp.entity.User;
import com.example.taskapp.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class TaskService {

    private final TaskRepository tasks;
    private final UserRepository users;

    public TaskService(
        TaskRepository tasks,
        UserRepository users
    ) {
        this.tasks = tasks;
        this.users = users;
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findAll(
            String keyword,
            String status,
            Long userId
    ) {
        String normalizedKeyword = keyword == null || keyword.isBlank() ? "" : keyword.trim();
        TaskStatus taskStatus = null;
        if (status != null && !status.isBlank()) {
            taskStatus = TaskStatus.valueOf(status);
        }
        return tasks.search(
                normalizedKeyword,
                taskStatus,
                userId
            )
            .stream()
            .map(TaskResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse findById(Long id) {
        Task task = tasks.findById(id)
                .orElseThrow(()
                        -> new NotFoundException("タスクが見つかりません。"));
        return TaskResponse.from(task);
    }

    public SummaryResponse summary() {
        return new SummaryResponse(
                tasks.count(),
                tasks.countByStatus(TaskStatus.TODO),
                tasks.countByStatus(TaskStatus.DOING),
                tasks.countByStatus(TaskStatus.DONE)
        );

    }

    @Transactional
    public TaskResponse create(TaskRequest request) {

        User user = users.findById(request.getUserId())
                .orElseThrow(()
                        -> new IllegalArgumentException("担当者が見つかりません。"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        LocalDateTime now = LocalDateTime.now();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        Task saved = tasks.save(task);
        
        return TaskResponse.from(saved);
    }

    @Transactional
    public TaskResponse update(Long id, TaskRequest request) {
        Task task = tasks.findById(id)
                .orElseThrow(()
                        -> new NotFoundException("タスクが見つかりません。")); 

        User user = users.findById(request.getUserId())
                .orElseThrow(()
                        -> new IllegalArgumentException("担当者が見つかりません。"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        task.setUpdatedAt(LocalDateTime.now());

        Task saved = tasks.save(task);

        return TaskResponse.from(saved);
    }

    @Transactional
    public void delete(Long id) {
        Task task = tasks.findById(id)
                .orElseThrow(()
                        -> new NotFoundException("タスクが見つかりません。"));
        tasks.delete(task);
    }
}
