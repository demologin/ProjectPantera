package com.javarush.zyibin.service;

import com.javarush.zyibin.exception.ValidationException;
import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.validation.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserService userService;

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationService(userService);
    }

    @Test
    void shouldRegisterUser_whenDataIsValid() {
        String username = "newuser";
        String password = "password123";
        String email = "newuser@example.com";
        
        User expectedUser = new User(1L, username, "hashedpassword", email, Role.USER);
        
        when(userService.register(username, password, email)).thenReturn(expectedUser);
        
        User result = registrationService.registerUser(username, password, email);
        
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        
        verify(userService).register(username, password, email);
    }

    @Test
    void shouldPropagateValidationException_whenValidationFails() {
        String username = "ab";
        String password = "123";
        String email = "invalid-email";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.registerUser(username, password, email));
        
        assertEquals("Username must be at least 3 characters", exception.getMessage());
        assertEquals("username", exception.getField());
        
        verify(userService, never()).register(any(), any(), any());
    }

    @Test
    void shouldPropagateUserServiceException_whenUserRegistrationFails() {
        String username = "existinguser";
        String password = "password123";
        String email = "existing@example.com";
        
        when(userService.register(username, password, email))
                .thenThrow(new IllegalStateException("User already exists"));
        
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> registrationService.registerUser(username, password, email));
        
        assertEquals("User already exists", exception.getMessage());
        
        verify(userService).register(username, password, email);
    }

    @Test
    void shouldRegisterAdminUser_whenUsernameIsAdmin() {
        String username = "admin";
        String password = "adminpass";
        String email = "admin@example.com";
        
        User adminUser = new User(1L, username, "hashedadminpass", email, Role.ADMIN);
        
        when(userService.register(username, password, email)).thenReturn(adminUser);
        
        User result = registrationService.registerUser(username, password, email);
        
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        
        verify(userService).register(username, password, email);
    }

    @Test
    void shouldHandleNullParameters_gracefully() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.registerUser(null, "password123", "test@example.com"));
        
        assertEquals("Username cannot be empty", exception.getMessage());
        assertEquals("username", exception.getField());
        
        verify(userService, never()).register(any(), any(), any());
    }
}
