package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game implements AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questId;

    private Long userId;

    private Long currentQuestionId;

    @Enumerated(EnumType.STRING)
    private GameState gameState;
}
