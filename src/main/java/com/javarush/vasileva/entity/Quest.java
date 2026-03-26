package com.javarush.vasileva.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.javarush.vasileva.mapper.QuestJsonSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@JsonSerialize(using = QuestJsonSerializer.class)
@Entity
@Table(name = "quest")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String text;
    private Long startQuestionId;
    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
    private String image;
}
