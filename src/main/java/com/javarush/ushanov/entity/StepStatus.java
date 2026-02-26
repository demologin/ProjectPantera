package com.javarush.ushanov.entity;

/**
 * Статус шага квеста.
 *
 * PLAYING — обычный шаг, игра продолжается
 * WIN — финальный шаг с победой
 * LOSE — финальный шаг с поражением
 */
public enum StepStatus {
    PLAYING,
    WIN,
    LOSE
}
