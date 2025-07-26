package com.example.gajendra_v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.gajendra_v1.exception.ToDoCollectionException;
import com.example.gajendra_v1.model.Todo;
import com.example.gajendra_v1.repository.TodoRepository;
import com.example.gajendra_v1.service.ToDoService;

import jakarta.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TodoController {
    
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private ToDoService toDoService;

    @GetMapping("/")
    public ResponseEntity<?> nothing(){
        return new ResponseEntity<>("Nothing Is Running", HttpStatus.OK);
    }
    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<Todo> todos = toDoService.getAllTodo();
        return new ResponseEntity<>(todos,todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        // if (todos.size() > 0) {
        //     return new ResponseEntity<List<Todo>>(todos, HttpStatus.OK);
        // }else{
        //     return new ResponseEntity<>("no tools available", HttpStatus.NOT_FOUND);
        // }
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTODO(@RequestBody Todo todo) {
        try{
            // todo.setCreatedAt(new Date(System.currentTimeMillis()));
            // todoRepository.save(todo);
            toDoService.createTODO(todo);
            return new ResponseEntity<Todo>(todo, HttpStatus.OK);
        }catch(ConstraintViolationException e){
            return new  ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch(ToDoCollectionException e){
            return new  ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTODO(@PathVariable("id") String id) {
        try {
            Todo todo = toDoService.getSingleTodo(id);  // get the Todo or throw
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (ToDoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateByID(@PathVariable("id") String id, @RequestBody Todo todo) {
        try{
        toDoService.getUpdateTodo(id, todo);
            return new  ResponseEntity<>("todo updated with id: "+id, HttpStatus.OK);
        }catch(ConstraintViolationException e){
            return new  ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

        }catch(ToDoCollectionException e){
            return new  ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        // try{
        //    Optional<Todo> todoOptional =  todoRepository.findById(id);
        //     if (todoOptional.isPresent()) {
        //         Todo updatedTodo = todoOptional.get();
        //         updatedTodo.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : updatedTodo.getCompleted());
        //         updatedTodo.setTodo(todo.getTodo() != null ? todo.getTodo() : updatedTodo.getTodo());
        //         updatedTodo.setDescription(todo.getDescription() != null ? todo.getDescription() : updatedTodo.getTodo());
        //         updatedTodo.setUpdatedAt(new Date(System.currentTimeMillis()));
        //         todoRepository.save(updatedTodo);
        //         return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        //     }else{
        //         return new ResponseEntity<>("No Data Found for id: " + id, HttpStatus.NOT_FOUND);
        //     }
        // }catch(Exception e){
        //     return new  ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        // }
    }

    
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteSingleTODO(@PathVariable("id") String id) {
        try{
            // todoRepository.deleteById(id);
            toDoService.deleteTodoById(id);
            return new ResponseEntity<>("Data Deleted for id: " + id, HttpStatus.OK);
        }catch(ToDoCollectionException e){
            return new  ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
}
