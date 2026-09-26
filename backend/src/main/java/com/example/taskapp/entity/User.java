package com.example.taskapp.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="users")
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String name;
 @Column(nullable=false,length=255) private String email;
 @Column(length=100) private String department;
 @Column(name="created_at",nullable=false) private LocalDateTime createdAt;
 @Column(name="updated_at",nullable=false) private LocalDateTime updatedAt;
 @PrePersist void pre(){var n=LocalDateTime.now();createdAt=n;updatedAt=n;} @PreUpdate void upd(){updatedAt=LocalDateTime.now();}
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getDepartment(){return department;}
}
