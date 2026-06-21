package com.javarush.aleinik.model;


import com.javarush.aleinik.model.enums.QuestStepResult;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestStep {
    private Long id;
    private String text;
    private Long questId;
    private QuestStepResult result;
    private List<Choice> choices;
}
