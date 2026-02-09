package com.javarush.popkov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements AbstractEntity {
    private Long id;
    private String login;
    private String password;
    private Role role;
    private Gender gender;
    private String imageId;
    private final Collection<Quest> quests = new ArrayList<>();
    private final Collection<Game> games = new ArrayList<>();
    public String getImage() { //TODO move to DTO
        if (imageId != null && !imageId.isBlank()) {
            int dotIndex = imageId.lastIndexOf('.');
            return dotIndex > 0
                    ? imageId.substring(0, dotIndex)
                    : imageId;
        }
        if (gender == Gender.FEMALE) {
            return "female";
        }
        if (gender == Gender.MALE) {
            return "male";
        }
        return "no-image";
    }

}
