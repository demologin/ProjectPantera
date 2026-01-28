package com.javarush.zyibin.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void shouldHashPassword_whenPasswordIsProvided() {
        String password = "testPassword123";
        
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
        assertNotEquals(password, hashedPassword);
        assertTrue(hashedPassword.length() > 0);
    }

    @Test
    void shouldGenerateConsistentHash_forSamePassword() {
        String password = "consistentPassword";
        
        String hash1 = PasswordUtil.hashPassword(password);
        String hash2 = PasswordUtil.hashPassword(password);
        
        assertEquals(hash1, hash2);
    }

    @Test
    void shouldGenerateDifferentHashes_forDifferentPasswords() {
        String password1 = "password1";
        String password2 = "password2";
        
        String hash1 = PasswordUtil.hashPassword(password1);
        String hash2 = PasswordUtil.hashPassword(password2);
        
        assertNotEquals(hash1, hash2);
    }

    @Test
    void shouldHandleEmptyPassword() {
        String password = "";
        
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void shouldHandleSpecialCharacters() {
        String password = "p@ssw0rd!#$%^&*()_+-=[]{}|;:,.<>?";
        
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void shouldHandleUnicodeCharacters() {
        String password = "пароль123";
        
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void shouldHandleLongPasswords() {
        String password = "a".repeat(1000);
        
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void shouldGenerateFixedLengthHashes() {
        String password1 = "short";
        String password2 = "muchLongerPasswordWithManyCharacters123456789";
        
        String hash1 = PasswordUtil.hashPassword(password1);
        String hash2 = PasswordUtil.hashPassword(password2);
        
        assertEquals(hash1.length(), hash2.length());
        assertEquals(64, hash1.length());
        assertEquals(64, hash2.length());
    }

    @Test
    void shouldContainOnlyHexCharacters() {
        String password = "testPassword";
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        assertTrue(hashedPassword.matches("[0-9a-fA-F]+"));
    }

    @Test
    void shouldBeCaseSensitive() {
        String password1 = "Password";
        String password2 = "password";
        
        String hash1 = PasswordUtil.hashPassword(password1);
        String hash2 = PasswordUtil.hashPassword(password2);
        
        assertNotEquals(hash1, hash2);
    }

    @Test
    void shouldThrowException_whenUtilityClassInstantiationIsAttempted() {
        assertThrows(UnsupportedOperationException.class, () -> {
            try {
                Class<?> clazz = PasswordUtil.class;
                java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (Exception e) {
                if (e.getCause() instanceof UnsupportedOperationException) {
                    throw (UnsupportedOperationException) e.getCause();
                }
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void shouldHandleCommonPasswords() {
        String[] commonPasswords = {
                "123456",
                "password",
                "123456789",
                "12345678",
                "12345",
                "1234567",
                "1234567890",
                "qwerty",
                "abc123",
                "password123"
        };
        
        for (String password : commonPasswords) {
            String hashedPassword = PasswordUtil.hashPassword(password);
            
            assertNotNull(hashedPassword);
            assertFalse(hashedPassword.isEmpty());
            assertNotEquals(password, hashedPassword);
            assertEquals(64, hashedPassword.length());
        }
    }
}
