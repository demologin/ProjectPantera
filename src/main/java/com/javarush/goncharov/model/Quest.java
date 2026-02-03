package com.javarush.goncharov.model;


import lombok.*;

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
}
