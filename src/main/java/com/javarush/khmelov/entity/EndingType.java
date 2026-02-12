package com.javarush.khmelov.entity;

public enum EndingType {
    WIN,
    LOSE;

    public static EndingType fromRussian(String s) {
        String val = s == null ? "" : s.trim().toLowerCase();
        if (val.contains("побед")) return WIN;
        if (val.contains("поражен")) return LOSE;
        throw new IllegalArgumentException("Unknown END value: " + s);
    }
}
