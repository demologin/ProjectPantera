package com.javarush.golikov.quest.model;

import com.javarush.golikov.quest.auth.Role;

public record User(String login, String password, Role role) {

    public String getLogin() {
        return login;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}