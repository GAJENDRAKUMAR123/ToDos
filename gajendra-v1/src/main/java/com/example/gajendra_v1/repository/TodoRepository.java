package com.example.gajendra_v1.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.gajendra_v1.model.Todo;
import java.util.Optional;


@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

    @Query("{'todo': ?0}")
    Optional<Todo> findByTodo(String id);
    
}
