package com.javarush.trukhanova.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Answer {
    private final String text;
    private final int nextStepId;
}