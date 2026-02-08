package com.javarush.goncharov.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Quest{
    private Long id;
    private String name;
    private String authorName;
    private String text;
    private Long developerId;
    private Long startQuestionId;
    private final Collection<Question> questions = new ArrayList<>();
}
