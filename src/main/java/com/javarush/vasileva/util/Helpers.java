package com.javarush.vasileva.util;

public class Helpers {

    private Helpers() {
    }

    public static Long parseStringToLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quest id");
        }
    }
}
