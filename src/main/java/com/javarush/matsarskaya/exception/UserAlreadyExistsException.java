package com.javarush.matsarskaya.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("Пользователь с именем '" + username + "' уже существует");
    }

    public UserAlreadyExistsException(String username, Throwable cause) {
        super("Пользователь с именем '" + username + "' уже существует", cause);
    }
}
