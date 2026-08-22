package com.javarush.aleinik.cache.mapper;

import com.javarush.aleinik.cache.dto.ChoiceCacheDto;
import com.javarush.aleinik.cache.dto.QuestStepCacheDto;
import com.javarush.aleinik.model.Choice;
import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.model.QuestStep;

import java.util.List;

public class QuestStepCacheMapper {


    public QuestStepCacheDto toCacheDto(QuestStep step) {

        return QuestStepCacheDto.builder()
                .questId(step.getQuestId())
                .stepId(step.getStepId())
                .result(step.getResult())
                .text(step.getText())
                .choices(toChoiceCacheDto(step.getChoices()))
                .build();
    }

    public QuestStep fromCacheDto( QuestStepCacheDto dto) {
        Quest quest = Quest.builder()
                .id(dto.getQuestId())
                .build();

        QuestStep step = QuestStep.builder()
                .stepId(dto.getStepId())
                .text(dto.getText())
                .result(dto.getResult())
                .quest(quest)
                .build();

        step.setChoices(fromChoiceCacheDto(step, dto.getChoices()));
        return step;
    }


    private List<ChoiceCacheDto> toChoiceCacheDto(List<Choice> choices) {
        if (choices == null || choices.isEmpty()) {
            return List.of();
        }
        return choices.stream()
                .map(
                        choice ->
                                ChoiceCacheDto.builder()
                                        .text(choice.getText())
                                        .nextStepId(choice.getNextStepId())
                                        .choiceId(choice.getChoiceId())
                                        .build()
                )
                .toList();
    }

    private List<Choice> fromChoiceCacheDto(QuestStep step, List<ChoiceCacheDto> dto) {
        if (dto == null || dto.isEmpty()) {
            return List.of();
        }

        return dto.stream()
                .map(
                        choiceDto ->
                                Choice.builder()
                                        .choiceId(choiceDto.getChoiceId())
                                        .text(choiceDto.getText())
                                        .nextStepId(choiceDto.getNextStepId())
                                        .questStep(step)
                                        .build()

                ).toList();
    }


}
