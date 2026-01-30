package com.javarush.chebotarev.component;

import jakarta.servlet.http.HttpSession;

public class Utils {

    private Utils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T extractAttribute(HttpSession currentSession,
                                         String attributeName,
                                         Class<T> attributeType) {
        Object attribute = currentSession.getAttribute(attributeName);
        if (attribute != null) {
            Class<?> attributeClass = attribute.getClass();
            if (attributeClass.equals(attributeType)) {
                return (T) attribute;
            }
        }
        currentSession.invalidate();
        throw new RuntimeException("Session is broken, try one more time");
    }
}
