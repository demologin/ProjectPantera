package com.javarush.aleinik.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javarush.aleinik.model.enums.QuestStepResult;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name="quest_step",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_quest_step_quest_step",
                columnNames = {"quest_id", "step_id"}
        )
)
public class QuestStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    @Column(name="step_id", nullable = false)
    private Long stepId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Quest quest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestStepResult result;

    @OneToMany(
           mappedBy = "questStep",
           cascade = CascadeType.ALL,
           orphanRemoval = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Choice> choices = new ArrayList<>();

    @Transient
    public Long getQuestId() {
        return quest != null ? quest.getId() : null;
    }
}
