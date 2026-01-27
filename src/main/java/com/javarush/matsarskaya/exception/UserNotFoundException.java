package com.javarush.matsarskaya.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String username) {
    super("Пользователь с именем '" + username + "' не найден");
  }

  public UserNotFoundException(String username, Throwable cause) {
    super("Пользователь с именем '" + username + "' не найден", cause);
  }
}
