package com.onofftaxi.backend.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(String message,
                             Throwable cause){
        super(message,cause);
    }
    public NotFoundException(
            String entityName,
            Long id){
        this(String.format("Cannot find entity %s with id %s",entityName,id));
    }    public NotFoundException(
            String entityName,
            String userName){
        this(String.format("Cannot find entity %s with enumeratedName %s",entityName,userName));
    }
}
