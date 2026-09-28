package com.example.taskapp.controller;

import com.example.taskapp.dto.UserResponse; 
import com.example.taskapp.entity.User; 
import com.example.taskapp.exception.NotFoundException; 
import com.example.taskapp.repository.UserRepository; 
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/users")
public class UserController {
    private final UserRepository repo; 
    public UserController(UserRepository r){repo=r;}
    
    @GetMapping("/{id}") 
    public UserResponse get(@PathVariable Long id){
        User u=repo.findById(id).orElseThrow(
            ()->new NotFoundException("担当者が見つかりません。"));
        return new UserResponse(u.getId(),u.getName(),u.getEmail(),u.getDepartment());
    }
}
