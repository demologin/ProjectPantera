package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient
    private final Collection<Quest> quests = new ArrayList<>();

    @Transient
    private final Collection<Game> games = new ArrayList<>();

    public String getImage() { //TODO move to DTO
        return "user-" + id;
    }


}
