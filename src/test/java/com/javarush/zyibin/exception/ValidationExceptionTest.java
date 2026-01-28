package com.javarush.zyibin.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {

    @Test
    void shouldCreateValidationException_withMessageAndField() {
        String message = "Invalid input";
        String field = "username";
        
        ValidationException exception = new ValidationException(message, field);
        
        assertEquals(message, exception.getMessage());
        assertEquals(field, exception.getField());
        assertEquals("VALIDATION_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldCreateValidationException_withMessageFieldAndErrorCode() {
        String message = "Invalid email format";
        String field = "email";
        String errorCode = "EMAIL_FORMAT_INVALID";
        
        ValidationException exception = new ValidationException(message, field, errorCode);
        
        assertEquals(message, exception.getMessage());
        assertEquals(field, exception.getField());
        assertEquals(errorCode, exception.getErrorCode());
    }

    @Test
    void shouldCreateUsernameValidationException() {
        String message = "Username is too short";
        
        ValidationException exception = ValidationException.username(message);
        
        assertEquals(message, exception.getMessage());
        assertEquals("username", exception.getField());
        assertEquals("USERNAME_INVALID", exception.getErrorCode());
    }

    @Test
    void shouldCreatePasswordValidationException() {
        String message = "Password must contain numbers";
        
        ValidationException exception = ValidationException.password(message);
        
        assertEquals(message, exception.getMessage());
        assertEquals("password", exception.getField());
        assertEquals("PASSWORD_INVALID", exception.getErrorCode());
    }

    @Test
    void shouldCreateEmailValidationException() {
        String message = "Email format is invalid";
        
        ValidationException exception = ValidationException.email(message);
        
        assertEquals(message, exception.getMessage());
        assertEquals("email", exception.getField());
        assertEquals("EMAIL_INVALID", exception.getErrorCode());
    }

    @Test
    void shouldCreateGeneralValidationException() {
        String message = "General validation error";
        
        ValidationException exception = ValidationException.general(message);
        
        assertEquals(message, exception.getMessage());
        assertEquals("general", exception.getField());
        assertEquals("GENERAL_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldInheritFromRuntimeException() {
        ValidationException exception = new ValidationException("Test message", "test");
        
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldHandleNullMessage() {
        String field = "test";
        
        ValidationException exception = new ValidationException(null, field);
        
        assertNull(exception.getMessage());
        assertEquals(field, exception.getField());
        assertEquals("VALIDATION_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldHandleNullField() {
        String message = "Test message";
        
        ValidationException exception = new ValidationException(message, null);
        
        assertEquals(message, exception.getMessage());
        assertNull(exception.getField());
        assertEquals("VALIDATION_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldHandleEmptyMessage() {
        String message = "";
        String field = "test";
        
        ValidationException exception = new ValidationException(message, field);
        
        assertEquals(message, exception.getMessage());
        assertEquals(field, exception.getField());
        assertEquals("VALIDATION_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldHandleEmptyField() {
        String message = "Test message";
        String field = "";
        
        ValidationException exception = new ValidationException(message, field);
        
        assertEquals(message, exception.getMessage());
        assertEquals(field, exception.getField());
        assertEquals("VALIDATION_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldHandleNullErrorCode() {
        String message = "Test message";
        String field = "test";
        
        ValidationException exception = new ValidationException(message, field, null);
        
        assertEquals(message, exception.getMessage());
        assertEquals(field, exception.getField());
        assertNull(exception.getErrorCode());
    }

    @Test
    void shouldCreateDifferentExceptionTypes() {
        ValidationException usernameException = ValidationException.username("Username error");
        ValidationException passwordException = ValidationException.password("Password error");
        ValidationException emailException = ValidationException.email("Email error");
        ValidationException generalException = ValidationException.general("General error");
        
        assertEquals("USERNAME_INVALID", usernameException.getErrorCode());
        assertEquals("PASSWORD_INVALID", passwordException.getErrorCode());
        assertEquals("EMAIL_INVALID", emailException.getErrorCode());
        assertEquals("GENERAL_ERROR", generalException.getErrorCode());
        
        assertEquals("username", usernameException.getField());
        assertEquals("password", passwordException.getField());
        assertEquals("email", emailException.getField());
        assertEquals("general", generalException.getField());
    }

    @Test
    void shouldBeCatchableAsRuntimeException() {
        ValidationException validationException = ValidationException.username("Test error");
        
        RuntimeException caughtException = assertThrows(RuntimeException.class, () -> {
            throw validationException;
        });
        
        assertTrue(caughtException instanceof ValidationException);
        assertEquals("Test error", caughtException.getMessage());
    }
}
