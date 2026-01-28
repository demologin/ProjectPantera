package com.javarush.zyibin.validation;

import com.javarush.zyibin.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

    private UserValidation userValidation;

    @BeforeEach
    void setUp() {
        userValidation = new UserValidation();
    }

    @Test
    void shouldValidateRegistration_whenAllDataIsValid() {
        String username = "validuser";
        String password = "password123";
        String email = "valid@example.com";
        
        assertDoesNotThrow(() -> userValidation.validateRegistration(username, password, email));
    }

    @Test
    void shouldValidateLogin_whenDataIsValid() {
        String username = "testuser";
        String password = "password123";
        
        assertDoesNotThrow(() -> userValidation.validateLogin(username, password));
    }

    @Test
    void shouldThrowException_whenLoginUsernameIsEmpty() {
        String username = "";
        String password = "password123";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateLogin(username, password));
        
        assertEquals("Username cannot be empty", exception.getMessage());
        assertEquals("username", exception.getField());
        assertEquals("USERNAME_INVALID", exception.getErrorCode());
    }

    @Test
    void shouldThrowException_whenLoginUsernameIsNull() {
        String username = null;
        String password = "password123";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateLogin(username, password));
        
        assertEquals("Username cannot be empty", exception.getMessage());
        assertEquals("username", exception.getField());
    }

    @Test
    void shouldThrowException_whenLoginPasswordIsEmpty() {
        String username = "testuser";
        String password = "";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateLogin(username, password));
        
        assertEquals("Password cannot be empty", exception.getMessage());
        assertEquals("password", exception.getField());
        assertEquals("PASSWORD_INVALID", exception.getErrorCode());
    }

    @Test
    void shouldThrowException_whenLoginPasswordIsNull() {
        String username = "testuser";
        String password = null;
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateLogin(username, password));
        
        assertEquals("Password cannot be empty", exception.getMessage());
        assertEquals("password", exception.getField());
    }

    @Test
    void shouldValidateUsername_whenValid() {
        String[] validUsernames = {
                "user",
                "test123",
                "user_name",
                "user-name",
                "validusername123",
                "User_Name"
        };
        
        for (String username : validUsernames) {
            assertDoesNotThrow(() -> userValidation.validateUsername(username));
        }
    }

    @Test
    void shouldThrowException_whenUsernameIsTooShort() {
        String username = "ab";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateUsername(username));
        
        assertEquals("Username must be at least 3 characters", exception.getMessage());
        assertEquals("username", exception.getField());
    }

    @Test
    void shouldThrowException_whenUsernameIsTooLong() {
        String username = "a".repeat(21);
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateUsername(username));
        
        assertEquals("Username must be at most 20 characters", exception.getMessage());
        assertEquals("username", exception.getField());
    }

    @Test
    void shouldThrowException_whenUsernameContainsInvalidCharacters() {
        String[] invalidUsernames = {
                "user@name",
                "user.name",
                "user name",
                "user#name",
                "user+name"
        };
        
        for (String username : invalidUsernames) {
            ValidationException exception = assertThrows(ValidationException.class,
                    () -> userValidation.validateUsername(username));
            
            assertEquals("Username can only contain letters, numbers, underscores and hyphens", exception.getMessage());
            assertEquals("username", exception.getField());
        }
    }

    @Test
    void shouldValidatePassword_whenValid() {
        String[] validPasswords = {
                "123456",
                "password",
                "Password123",
                "securepass",
                "a".repeat(100)
        };
        
        for (String password : validPasswords) {
            assertDoesNotThrow(() -> userValidation.validatePassword(password));
        }
    }

    @Test
    void shouldThrowException_whenPasswordIsTooShort() {
        String password = "12345";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validatePassword(password));
        
        assertEquals("Password must be at least 6 characters", exception.getMessage());
        assertEquals("password", exception.getField());
    }

    @Test
    void shouldThrowException_whenPasswordIsTooLong() {
        String password = "a".repeat(101);
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validatePassword(password));
        
        assertEquals("Password must be no more than 100 characters", exception.getMessage());
        assertEquals("password", exception.getField());
    }

    @Test
    void shouldValidateEmail_whenValid() {
        String[] validEmails = {
                "test@example.com",
                "user.name@domain.co.uk",
                "user+tag@example.org",
                "user123@test-domain.com",
                "a@b.co"
        };
        
        for (String email : validEmails) {
            assertDoesNotThrow(() -> userValidation.validateEmail(email));
        }
    }

    @Test
    void shouldThrowException_whenEmailIsEmpty() {
        String email = "";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateEmail(email));
        
        assertEquals("Email cannot be empty", exception.getMessage());
        assertEquals("email", exception.getField());
    }

    @Test
    void shouldThrowException_whenEmailIsNull() {
        String email = null;
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateEmail(email));
        
        assertEquals("Email cannot be empty", exception.getMessage());
        assertEquals("email", exception.getField());
    }

    @Test
    void shouldThrowException_whenEmailFormatIsInvalid() {
        String invalidEmail = "invalid-email";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateEmail(invalidEmail));
        
        assertEquals("Invalid email format", exception.getMessage());
        assertEquals("email", exception.getField());
    }

    @Test
    void shouldThrowException_whenEmailIsTooLong() {
        String email = "user@" + "a".repeat(95) + ".com";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateEmail(email));
        
        assertEquals("Email is too long", exception.getMessage());
        assertEquals("email", exception.getField());
    }

    @Test
    void shouldValidateProfile_whenDataIsValid() {
        String nickname = "Valid Nickname";
        String about = "Valid about section with reasonable length";
        
        assertDoesNotThrow(() -> userValidation.validateProfile(nickname, about));
    }

    @Test
    void shouldValidateProfile_whenDataIsNull() {
        assertDoesNotThrow(() -> userValidation.validateProfile(null, null));
    }

    @Test
    void shouldThrowException_whenNicknameIsTooLong() {
        String nickname = "a".repeat(51);
        String about = "Valid about";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateProfile(nickname, about));
        
        assertEquals("Nickname is too long (max 50 characters)", exception.getMessage());
        assertEquals("general", exception.getField());
    }

    @Test
    void shouldThrowException_whenAboutIsTooLong() {
        String nickname = "Valid nickname";
        String about = "a".repeat(501);
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userValidation.validateProfile(nickname, about));
        
        assertEquals("About section is too long (max 500 characters)", exception.getMessage());
        assertEquals("general", exception.getField());
    }

    @Test
    void shouldTrimWhitespaceInUsername() {
        String username = "  validuser  ";
        
        assertDoesNotThrow(() -> userValidation.validateUsername(username));
    }

    @Test
    void shouldTrimWhitespaceInEmail() {
        String email = "  test@example.com  ";
        
        assertDoesNotThrow(() -> userValidation.validateEmail(email));
    }

    @Test
    void shouldHandleEdgeCasesInRegistration() {
        assertThrows(ValidationException.class, 
                () -> userValidation.validateRegistration("", "", ""));
        
        assertThrows(ValidationException.class,
                () -> userValidation.validateRegistration("ab", "12345", "invalid"));
        
        assertDoesNotThrow(() -> userValidation.validateRegistration("valid", "123456", "valid@example.com"));
    }
}
