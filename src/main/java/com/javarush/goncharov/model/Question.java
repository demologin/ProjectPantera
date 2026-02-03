package com.javarush.goncharov.model;

import java.util.ArrayList;
import java.util.Collection;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Question {
    private Long id;
    private Long questId;
    private String text;
    private final Collection<Answer> answers = new ArrayList<>();
}
