package com.javarush.vasileva.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String login;

    private String email;

    private String password;

    private Role role;

    private int gameNumber;

    @SuppressWarnings("unused")
    public boolean isAdmin() {
        return role != null && role.equals(Role.ADMIN);
    }
}
