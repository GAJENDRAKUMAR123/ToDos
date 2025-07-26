package com.example.gajendra_v1.exception;

public class ToDoCollectionException extends Exception {
    
    private static final long serialVersionUID = 1L;
     
    public ToDoCollectionException(String message){
        super(message);
    }

    public static String NotFoundException(String id){
        return "ToDo with ID:"+id+" not found!!!";
    }
    public static String ToDoAlreadyExists(){
        return "ToDo Already Exist!!!";
    }
}
