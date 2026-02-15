package com.javarush.trukhanova.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String name;
    private int gamesPlayed;
    private String avatarPath;

    public Player(String name, String avatarPath) {
        this.name = name;
        this.avatarPath = avatarPath;
        this.gamesPlayed = 0;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }
}