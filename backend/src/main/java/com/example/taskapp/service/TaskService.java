package com.example.taskapp.service;

import com.example.taskapp.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.example.taskapp.dto.SummaryResponse;
import com.example.taskapp.entity.TaskStatus;

@Service
public class TaskService {
    private final TaskRepository tasks;

    public TaskService(TaskRepository tasks) {
        this.tasks = tasks;
    }

    public SummaryResponse summary() {
        return new SummaryResponse(
            tasks.count(),
            tasks.countByStatus(TaskStatus.TODO),
            tasks.countByStatus(TaskStatus.DOING),
            tasks.countByStatus(TaskStatus.DONE)
        );

    }
}
