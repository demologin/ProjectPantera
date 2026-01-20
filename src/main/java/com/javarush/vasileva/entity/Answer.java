package com.javarush.vasileva.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    private Long id;
    private Long questionId;
    private Long nextQuestionId;
    private String text;
    private String description;
}
