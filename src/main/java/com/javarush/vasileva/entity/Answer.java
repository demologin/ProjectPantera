package com.javarush.vasileva.entity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    private Long id;
    private Long questionId;
    private String nextQuestionId;
    private String text;
    private String description;
}
