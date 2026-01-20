package com.javarush.vasileva.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private Long id;
    private Long questId;
    private String text;
    private List<Answer> answers;
}
