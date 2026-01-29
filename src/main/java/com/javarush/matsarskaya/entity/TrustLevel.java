package com.javarush.matsarskaya.entity;

import java.util.Arrays;

//уровни доверия
public enum TrustLevel {
    CRITICAL(0, 49),
    LOW(50, 69),
    HIGH(70, 100);

    private final int min;
    private final int max;

    TrustLevel(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static TrustLevel fromValue(int trust) {
        return Arrays.stream(values())
                .filter(level -> trust >= level.min && trust <= level.max)
                .findFirst()
                .orElse(CRITICAL);
    }

    public boolean isSufficientForStage(int stageNumber) {
        // Для этапов 0, 1, 2 и 11 все уровни достаточны
        if (stageNumber <= 2 || stageNumber >= 11) {
            return true;
        }
        // Для этапов 7-10 только HIGH достаточен
        if (stageNumber >= 7) {
            return this == HIGH;
        }
        // Для этапов 3-6 LOW и HIGH достаточны
        return this != CRITICAL;
    }
}
