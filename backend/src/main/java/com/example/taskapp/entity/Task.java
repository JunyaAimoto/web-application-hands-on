package com.example.taskapp.entity;
import jakarta.persistence.*; import java.time.*;
@Entity @Table(name="tasks")
public class Task {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=200) private String title;
 @Column(columnDefinition="TEXT") private String description;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private User user;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private TaskStatus status;
 @Column(name="due_date") private LocalDate dueDate;
 @Column(name="created_at",nullable=false) private LocalDateTime createdAt;
 @Column(name="updated_at",nullable=false) private LocalDateTime updatedAt;
 @PrePersist void pre(){var n=LocalDateTime.now();createdAt=n;updatedAt=n;} @PreUpdate void upd(){updatedAt=LocalDateTime.now();}
 public Long getId(){return id;} public String getTitle(){return title;} public String getDescription(){return description;} public User getUser(){return user;}
 public TaskStatus getStatus(){return status;} public LocalDate getDueDate(){return dueDate;} public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
 public void setTitle(String v){title=v;} public void setDescription(String v){description=v;} public void setUser(User v){user=v;} public void setStatus(TaskStatus v){status=v;} public void setDueDate(LocalDate v){dueDate=v;}
}
