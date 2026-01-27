package com.javarush.matsarskaya.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Неверное имя пользователя или пароль");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
