package com.javarush.aleinik.model;

import lombok.*;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quest {
    private Long id;
    private String title;
    private String description;
    private Long firstStepId;
}
