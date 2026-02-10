package com.javarush.trukhanova.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private String name;
    private int gamesPlayed;

    public Player(String name) {
        this.name = name;
        this.gamesPlayed = 0;
    }
}

