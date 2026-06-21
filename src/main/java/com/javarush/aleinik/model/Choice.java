package com.javarush.aleinik.model;
import lombok.*;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    private Long id;
    private String text;
    private Long nextStepId;
}
