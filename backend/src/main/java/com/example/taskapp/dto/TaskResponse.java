package com.example.taskapp.dto;
import java.time.*;
public record TaskResponse(Long id,String title,String description,Long userId,String userName,String status,LocalDate dueDate,LocalDateTime createdAt,LocalDateTime updatedAt){}
