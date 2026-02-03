package com.javarush.goncharov.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Answer {
    private Long id;
    private Long questionId;
    private String text;
    private Long nextQuestionId;
}
