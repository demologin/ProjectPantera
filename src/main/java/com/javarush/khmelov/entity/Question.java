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
public class Question implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questId;

    private String text;

    @Enumerated(EnumType.STRING)
    private GameState gameState;

    @Transient
    private final Collection<Answer> answers = new ArrayList<>();

    public String getImage() {
        return "question-" + id;
    }
}
