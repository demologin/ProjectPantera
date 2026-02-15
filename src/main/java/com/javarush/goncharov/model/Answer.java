package com.javarush.goncharov.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {
    Long id;
    Long questionId;
    String text;
    String questName;
    Long nextQuestionId;
}
