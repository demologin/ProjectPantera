package com.javarush.ushanov.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * Один шаг (узел) квеста.
 *
 * Каждый шаг имеет:
 *  - уникальный id
 *  - текст вопроса/описания ситуации, который видит игрок
 *  - статус: PLAYING (обычный шаг), WIN (победа), LOSE (поражение)
 *  - карту вариантов ответов: текст кнопки -> id следующего шага
 *    (если шаг финальный — options будет пустой Map)
 */
@Getter
@Builder
public class QuestStep {

    private final int id;
    private final String description;
    private final StepStatus status;

    /**
     * Ключ — текст варианта ответа (то, что видит игрок на кнопке)
     * Значение — id шага, на который переходим при выборе этого варианта
     */
    private final Map<String, Integer> options;

    public boolean isCompleted() {
        return status == StepStatus.WIN || status == StepStatus.LOSE;
    }
}
