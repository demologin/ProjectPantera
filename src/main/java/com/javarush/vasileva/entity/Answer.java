package com.javarush.vasileva.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "answer", schema = "public")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    @Column(name = "next_question_label")
    private String nextQuestionLabel;
    @Column(name = "text")
    private String text;
    @Column(name = "description")
    private String description;
}
