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
public class Quest implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String text;

    @Column(name = "users_id")
    private Long authorId;

    private Long startQuestionId;

    @Transient
    private final Collection<Question> questions = new ArrayList<>();

}
