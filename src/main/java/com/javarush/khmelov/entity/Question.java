package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "answers")
@Cacheable
@Cache(
        usage = CacheConcurrencyStrategy.READ_ONLY,
        region = "MyQuestion"
)
public class Question implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quest_id")
    private Long questId;

    private String text;

    @Enumerated(EnumType.STRING)
    private GameState gameState;

    @OneToMany(mappedBy = "questionId")

    //@Fetch(value = FetchMode.SUBSELECT)
    private final Collection<Answer> answers = new ArrayList<>();

    public String getImage() {
        return "question-" + id;
    }
}
