package com.example.gajendra_v1.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "en")
public class Todo {
    @Id
    private String id;
    
    @NotNull(message = "todo can't be null")
    private String todo;

    @NotNull(message = "description can't be null")
    private String description;

    @NotNull(message = "completed can't be null")
    private Boolean completed;

    private Date createdAt;

    private Date updatedAt; 

}
