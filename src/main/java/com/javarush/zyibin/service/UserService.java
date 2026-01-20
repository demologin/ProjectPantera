package com.javarush.zyibin.service;

import com.javarush.zyibin.model.User;

public interface UserService {

    User register(String username, String rawPassword, String email);
}
