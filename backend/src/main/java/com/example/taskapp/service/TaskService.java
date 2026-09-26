package com.example.taskapp.service;
import com.example.taskapp.dto.*; import com.example.taskapp.entity.*; import com.example.taskapp.exception.NotFoundException; import com.example.taskapp.repository.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service @Transactional
public class TaskService {
 private final TaskRepository tasks; private final UserRepository users;
 public TaskService(TaskRepository t,UserRepository u){tasks=t;users=u;}
 @Transactional(readOnly=true) public List<TaskResponse> findAll(String k,TaskStatus s,Long u){return tasks.search(k==null||k.isBlank()?null:k.trim(),s,u).stream().map(this::r).toList();}
 @Transactional(readOnly=true) public TaskResponse findById(Long id){return r(tasks.findById(id).orElseThrow(()->new NotFoundException("タスクが見つかりません。")));}
 public TaskResponse create(TaskRequest x){Task t=new Task(); apply(t,x); return r(tasks.save(t));}
 public TaskResponse update(Long id,TaskRequest x){Task t=tasks.findById(id).orElseThrow(()->new NotFoundException("タスクが見つかりません。"));apply(t,x);return r(tasks.save(t));}
 public void delete(Long id){Task t=tasks.findById(id).orElseThrow(()->new NotFoundException("タスクが見つかりません。"));tasks.delete(t);}
 public SummaryResponse summary(){return new SummaryResponse(tasks.count(),tasks.countByStatus(TaskStatus.TODO),tasks.countByStatus(TaskStatus.DOING),tasks.countByStatus(TaskStatus.DONE));}
 private void apply(Task t,TaskRequest x){var u=users.findById(x.getUserId()).orElseThrow(()->new IllegalArgumentException("指定された担当者が存在しません。"));t.setTitle(x.getTitle().trim());t.setDescription(x.getDescription());t.setUser(u);t.setStatus(x.getStatus());t.setDueDate(x.getDueDate());}
 private TaskResponse r(Task t){return new TaskResponse(t.getId(),t.getTitle(),t.getDescription(),t.getUser().getId(),t.getUser().getName(),t.getStatus().name(),t.getDueDate(),t.getCreatedAt(),t.getUpdatedAt());}
}
