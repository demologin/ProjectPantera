package com.javarush.goncharov.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User{
    Long id;
    String login;
    String password;
    Role role;
    String email;
    final Collection<Quest> quests = new ArrayList<>();
    final Collection<Game> games = new ArrayList<>();
}
