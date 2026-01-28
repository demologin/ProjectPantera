package com.javarush.zyibin.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationExceptionTest {

    @Test
    void shouldCreateAuthenticationException_withMessageAndReason() {
        String message = "Authentication failed";
        String reason = "INVALID_CREDENTIALS";
        
        AuthenticationException exception = new AuthenticationException(message, reason);
        
        assertEquals(message, exception.getMessage());
        assertEquals(reason, exception.getReason());
    }

    @Test
    void shouldCreateInvalidCredentialsException() {
        AuthenticationException exception = AuthenticationException.invalidCredentials();
        
        assertEquals("Invalid login or password", exception.getMessage());
        assertEquals("INVALID_CREDENTIALS", exception.getReason());
    }

    @Test
    void shouldCreateUserBlockedException() {
        AuthenticationException exception = AuthenticationException.userBlocked();
        
        assertEquals("User is blocked", exception.getMessage());
        assertEquals("USER_BLOCKED", exception.getReason());
    }

    @Test
    void shouldCreateUserNotFoundException() {
        AuthenticationException exception = AuthenticationException.userNotFound();
        
        assertEquals("User not found", exception.getMessage());
        assertEquals("USER_NOT_FOUND", exception.getReason());
    }

    @Test
    void shouldInheritFromRuntimeException() {
        AuthenticationException exception = new AuthenticationException("Test message", "TEST_REASON");
        
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldHandleNullMessage() {
        String reason = "TEST_REASON";
        
        AuthenticationException exception = new AuthenticationException(null, reason);
        
        assertNull(exception.getMessage());
        assertEquals(reason, exception.getReason());
    }

    @Test
    void shouldHandleNullReason() {
        String message = "Test message";
        
        AuthenticationException exception = new AuthenticationException(message, null);
        
        assertEquals(message, exception.getMessage());
        assertNull(exception.getReason());
    }

    @Test
    void shouldHandleEmptyMessage() {
        String message = "";
        String reason = "TEST_REASON";
        
        AuthenticationException exception = new AuthenticationException(message, reason);
        
        assertEquals(message, exception.getMessage());
        assertEquals(reason, exception.getReason());
    }

    @Test
    void shouldHandleEmptyReason() {
        String message = "Test message";
        String reason = "";
        
        AuthenticationException exception = new AuthenticationException(message, reason);
        
        assertEquals(message, exception.getMessage());
        assertEquals(reason, exception.getReason());
    }

    @Test
    void shouldCreateDifferentExceptionTypes() {
        AuthenticationException invalidCredentialsException = AuthenticationException.invalidCredentials();
        AuthenticationException userBlockedException = AuthenticationException.userBlocked();
        AuthenticationException userNotFoundException = AuthenticationException.userNotFound();
        
        assertEquals("INVALID_CREDENTIALS", invalidCredentialsException.getReason());
        assertEquals("USER_BLOCKED", userBlockedException.getReason());
        assertEquals("USER_NOT_FOUND", userNotFoundException.getReason());
        
        assertEquals("Invalid login or password", invalidCredentialsException.getMessage());
        assertEquals("User is blocked", userBlockedException.getMessage());
        assertEquals("User not found", userNotFoundException.getMessage());
    }

    @Test
    void shouldBeCatchableAsRuntimeException() {
        AuthenticationException authException = AuthenticationException.invalidCredentials();
        
        RuntimeException caughtException = assertThrows(RuntimeException.class, () -> {
            throw authException;
        });
        
        assertTrue(caughtException instanceof AuthenticationException);
        assertEquals("Invalid login or password", caughtException.getMessage());
        assertEquals("INVALID_CREDENTIALS", ((AuthenticationException) caughtException).getReason());
    }

    @Test
    void shouldMaintainImmutabilityOfStaticMethods() {
        AuthenticationException exception1 = AuthenticationException.invalidCredentials();
        AuthenticationException exception2 = AuthenticationException.invalidCredentials();
        
        assertEquals(exception1.getMessage(), exception2.getMessage());
        assertEquals(exception1.getReason(), exception2.getReason());
        assertNotSame(exception1, exception2);
    }

    @Test
    void shouldHandleCustomMessageAndReason() {
        String customMessage = "Custom authentication error";
        String customReason = "CUSTOM_ERROR";
        
        AuthenticationException exception = new AuthenticationException(customMessage, customReason);
        
        assertEquals(customMessage, exception.getMessage());
        assertEquals(customReason, exception.getReason());
    }

    @Test
    void shouldWorkInTryCatchBlock() {
        AuthenticationException caughtException = null;
        
        try {
            throw AuthenticationException.userBlocked();
        } catch (AuthenticationException e) {
            caughtException = e;
        }
        
        assertNotNull(caughtException);
        assertEquals("User is blocked", caughtException.getMessage());
        assertEquals("USER_BLOCKED", caughtException.getReason());
    }
}
