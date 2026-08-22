package com.javarush.aleinik.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
        name = "choice",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_choice_step_choice",
                columnNames = {"quest_step_id", "choice_id"}
        )

    )
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    @Column(name = "choice_id", nullable = false)
    private Long choiceId;

    @Column(nullable = false)
    private String text;
    @Column(name = "next_step_id")
    private Long nextStepId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_step_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private QuestStep questStep;
}
