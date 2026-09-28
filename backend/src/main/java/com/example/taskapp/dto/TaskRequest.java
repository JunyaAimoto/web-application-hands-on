package com.example.taskapp.dto;

import com.example.taskapp.entity.TaskStatus; 
import jakarta.validation.constraints.*; 
import java.time.LocalDate;

public class TaskRequest {
    @NotBlank @Size(max=200) 
    private String title;
    
    private String description; 
    
    @NotNull 
    private Long userId; 

    @NotNull 
    private TaskStatus status; 
    
    private LocalDate dueDate;
    
    public String getTitle(){
        return title;
    } 

    public String getDescription(){
        return description;
    } 
    
    public Long getUserId(){
        return userId;
    } 
    
    public TaskStatus getStatus(){
        return status;
    } 
    
    public LocalDate getDueDate(){
        return dueDate;
    }
}
