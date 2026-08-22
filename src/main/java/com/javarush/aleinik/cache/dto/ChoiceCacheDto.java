package com.javarush.aleinik.cache.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceCacheDto {

    private Long choiceId;
    private String text;
    private Long nextStepId;
}
