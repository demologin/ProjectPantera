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
@ToString(exclude = {"questions", "author", "users", "text"})
public class Quest implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User author;

    @Column(name = "start_question_id")
    private Long startQuestionId;

    @OneToMany(mappedBy = "questId")
    private final Collection<Question> questions = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "game",
            inverseJoinColumns = @JoinColumn(
                    name = "quest_id",
                    referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "users_id",
                    referencedColumnName = "id")
    )
    private Collection<User> users;

}
