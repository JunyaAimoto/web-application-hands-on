package com.example.taskapp.exception;

import com.example.taskapp.dto.ErrorResponse; 
import org.springframework.http.*; 
import org.springframework.web.bind.annotation.*; 
import org.springframework.web.bind.MethodArgumentNotValidException; 
import java.time.*; 
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

 @ExceptionHandler(MethodArgumentNotValidException.class) 
 ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException e){
    Map<String,String> m=new LinkedHashMap<>(); 
    e.getBindingResult().getFieldErrors().forEach(x->m.put(x.getField(),x.getDefaultMessage()));
    return ResponseEntity.badRequest().body(
        new ErrorResponse(LocalDateTime.now(),400,"VALIDATION_ERROR","入力内容に誤りがあります。",m));
 }

 @ExceptionHandler(IllegalArgumentException.class) 
 ResponseEntity<ErrorResponse> bad(IllegalArgumentException e){
    return ResponseEntity.badRequest().body(
        new ErrorResponse(LocalDateTime.now(),400,"VALIDATION_ERROR",e.getMessage(),Map.of()));}
 
 @ExceptionHandler(NotFoundException.class) 
 ResponseEntity<ErrorResponse> notFound(NotFoundException e){
    return ResponseEntity.status(404).body(
        new ErrorResponse(LocalDateTime.now(),404,"NOT_FOUND",e.getMessage(),Map.of()));}
 
 @ExceptionHandler(Exception.class) 
 ResponseEntity<ErrorResponse> other(Exception e){
    return ResponseEntity.status(500).body(
        new ErrorResponse(LocalDateTime.now(),500,"INTERNAL_ERROR","サーバー内部でエラーが発生しました。",Map.of()));}
}
