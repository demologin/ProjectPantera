package com.javarush.popkov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public String getImage() { //TODO move to DTO
        if (imageId != null && !imageId.isBlank()) {
            return imageId;
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
