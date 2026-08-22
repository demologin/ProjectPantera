package com.javarush.aleinik.exception;

public class InvalidResourceNameException extends RuntimeException{

    public InvalidResourceNameException(String errorMessage){
        super(errorMessage);
    }
}
