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
public class Quest {
    private Long id;
    private String title;
    private String description;
    private String text;
    private Long startQuestionId;
    private List<Question> questions;
}
