package com.ahmad.BloggingPlatform.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String mesaage){
        super(mesaage);
    }
}
