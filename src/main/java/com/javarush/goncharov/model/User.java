package com.javarush.goncharov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User{
    private Long id;
    private String login;
    private String password;
    private Role role;
    private String email;
    private final Collection<Quest> quests = new ArrayList<>();
    private final Collection<Game> games = new ArrayList<>();
}
