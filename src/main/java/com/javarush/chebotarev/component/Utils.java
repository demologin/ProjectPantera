package com.javarush.chebotarev.component;

import jakarta.servlet.http.HttpSession;

public class Utils {

    private Utils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T tryExtractAttribute(HttpSession currentSession,
                                            String attributeName,
                                            Class<T> attributeClass) {
        Object attribute = currentSession.getAttribute(attributeName);
        if (attribute != null) {
            Class<?> extractedAttributeClass = attribute.getClass();
            if (extractedAttributeClass.equals(attributeClass)) {
                return (T) attribute;
            }
        }
        return null;
    }

    public static <T> T extractAttribute(HttpSession currentSession,
                                         String attributeName,
                                         Class<T> attributeClass) {
        T attribute = tryExtractAttribute(
                currentSession,
                attributeName,
                attributeClass
        );
        if (attribute != null) {
            return attribute;
        } else {
            currentSession.invalidate();
            throw new RuntimeException("Session is broken, try one more time");
        }
    }
}
