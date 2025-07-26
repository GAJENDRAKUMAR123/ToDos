package com.example.gajendra_v1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gajendra_v1.exception.ToDoCollectionException;
import com.example.gajendra_v1.model.Todo;
import com.example.gajendra_v1.repository.TodoRepository;

@Service
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void createTODO(Todo todo) throws ToDoCollectionException{
        Optional<Todo> toDoOptional = todoRepository.findByTodo(todo.getTodo());
        if(toDoOptional.isPresent()){
            throw new ToDoCollectionException(ToDoCollectionException.ToDoAlreadyExists());
        }else{
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }
    }

    @Override
    public List<Todo> getAllTodo() {
        List<Todo> todos = todoRepository.findAll();
        if (todos.size() >0 ) {
            return todos;
        }else{
            return new ArrayList<Todo>();
        }
    }

    @Override
    public Todo getSingleTodo(String id) throws ToDoCollectionException{
        Optional <Todo> optionalTodo = todoRepository.findById(id);
        if (!optionalTodo.isPresent()) {
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }else{
            return optionalTodo.get();
        }
    }

    @Override
    public void getUpdateTodo(String id, Todo todo) throws ToDoCollectionException{
        Optional <Todo> optionalTodo = todoRepository.findById(id);
        Optional <Todo> optionalTodoWithSameName = todoRepository.findByTodo(todo.getTodo());

        if (!optionalTodo.isPresent()) {
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }else{

            //if same name todo exist
            if (optionalTodoWithSameName.isPresent() && !optionalTodoWithSameName.get().getId().equals(id)) {
                throw new ToDoCollectionException(ToDoCollectionException.ToDoAlreadyExists());
            }

            Todo todoToUpdate = optionalTodo.get();
            todoToUpdate.setTodo(todo.getTodo());
            todoToUpdate.setDescription(todo.getDescription());
            todoToUpdate.setCompleted(todo.getCompleted());
            todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoToUpdate);
        }
    }

    
    @Override
    public void deleteTodoById(String id) throws ToDoCollectionException{
       Optional<Todo> optionalTodo =  todoRepository.findById(id);
        if (!optionalTodo.isPresent()) {
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }else{
            todoRepository.deleteById(id);
        }
    }
}
