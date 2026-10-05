package com.example.taskapp.service;

import com.example.taskapp.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.example.taskapp.dto.SummaryResponse;
import com.example.taskapp.entity.TaskStatus;
import com.example.taskapp.dto.TaskResponse; 
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository tasks;

    public TaskService(TaskRepository tasks) {
        this.tasks = tasks;
    }

    @Transactional(readOnly = true) 
    public List<TaskResponse> findAll() { 
        return tasks.findAll() 
            .stream() 
            .map(TaskResponse::from) 
            .toList(); 
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
