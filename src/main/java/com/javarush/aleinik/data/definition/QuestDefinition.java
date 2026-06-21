package com.javarush.aleinik.data.definition;

import com.javarush.aleinik.model.QuestStep;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestDefinition {
    private Long id;
    private String title;
    private String description;
    private Long firstStepId;

    private List<QuestStep> steps;

}
