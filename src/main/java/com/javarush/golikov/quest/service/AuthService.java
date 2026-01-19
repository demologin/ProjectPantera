package com.javarush.golikov.quest.service;

import com.javarush.golikov.quest.model.User;
import com.javarush.golikov.quest.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

public class AuthService {

    public User login(String login, String password) {
        User user = UserRepository.find(login);
        if (user != null && user.password().equals(password)) {
            return user;
        }
        return null;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}