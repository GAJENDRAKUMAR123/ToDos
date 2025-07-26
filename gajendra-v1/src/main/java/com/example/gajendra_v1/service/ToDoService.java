package com.example.gajendra_v1.service;

import java.util.List;

import com.example.gajendra_v1.exception.ToDoCollectionException;
import com.example.gajendra_v1.model.Todo;

import jakarta.validation.ConstraintViolationException;

public interface ToDoService{
    public void createTODO(Todo todo) throws ConstraintViolationException, ToDoCollectionException;

    public List<Todo> getAllTodo();

    public Todo getSingleTodo(String id) throws ToDoCollectionException;
    
    public void getUpdateTodo(String id, Todo todo) throws ToDoCollectionException;

    public void deleteTodoById(String id) throws ToDoCollectionException;

}
