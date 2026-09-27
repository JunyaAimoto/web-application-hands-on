package com.example.taskapp.controller;
import com.example.taskapp.dto.*; import com.example.taskapp.entity.TaskStatus; import com.example.taskapp.service.TaskService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/tasks")
public class TaskController {
 private final TaskService service; public TaskController(TaskService s){service=s;}
 @GetMapping public List<TaskResponse> list(@RequestParam(required=false)String keyword,@RequestParam(required=false)TaskStatus status,@RequestParam(required=false)Long userId){return service.findAll(keyword,status,userId);}
 @GetMapping("/{id}") public TaskResponse get(@PathVariable Long id){return service.findById(id);}
 @PostMapping public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest r){return ResponseEntity.status(201).body(service.create(r));}
 @PutMapping("/{id}") public TaskResponse update(@PathVariable Long id,@Valid @RequestBody TaskRequest r){return service.update(id,r);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}
