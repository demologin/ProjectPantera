package com.javarush.aleinik.cache.dto;

import com.javarush.aleinik.model.enums.QuestStepResult;
import lombok.*;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestStepCacheDto {

    private Long questId;
    private Long stepId;
    private String text;
    private QuestStepResult result;
    private List<ChoiceCacheDto> choices;

}
