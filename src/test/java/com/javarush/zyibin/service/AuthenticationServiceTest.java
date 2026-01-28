package com.javarush.zyibin.service;

import com.javarush.zyibin.exception.AuthenticationException;
import com.javarush.zyibin.exception.ValidationException;
import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import com.javarush.zyibin.util.PasswordUtil;
import com.javarush.zyibin.validation.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(userRepository);
    }

    @Test
    void shouldAuthenticateUser_whenCredentialsAreValid() {
        String username = "testuser";
        String password = "password123";
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        User user = new User(1L, username, hashedPassword, "test@example.com", Role.USER);
        
        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        
        User result = authenticationService.authenticate(username, password);
        
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userRepository).findByUserName(username);
    }

    @Test
    void shouldThrowAuthenticationException_whenUserNotFound() {
        String username = "nonexistent";
        String password = "password123";
        
        when(userRepository.findByUserName(username)).thenReturn(Optional.empty());
        
        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> authenticationService.authenticate(username, password));
        
        assertEquals("USER_NOT_FOUND", exception.getReason());
        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findByUserName(username);
    }

    @Test
    void shouldThrowAuthenticationException_whenPasswordIsIncorrect() {
        String username = "testuser";
        String correctPassword = "password123";
        String wrongPassword = "wrongpassword";
        String hashedPassword = PasswordUtil.hashPassword(correctPassword);
        
        User user = new User(1L, username, hashedPassword, "test@example.com", Role.USER);
        
        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        
        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> authenticationService.authenticate(username, wrongPassword));
        
        assertEquals("INVALID_CREDENTIALS", exception.getReason());
        assertEquals("Invalid login or password", exception.getMessage());
        verify(userRepository).findByUserName(username);
    }

    @Test
    void shouldThrowAuthenticationException_whenUserIsBlocked() {
        String username = "blockeduser";
        String password = "password123";
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        User user = new User(1L, username, hashedPassword, "test@example.com", Role.USER);
        user.setBlocked(true);
        
        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        
        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> authenticationService.authenticate(username, password));
        
        assertEquals("USER_BLOCKED", exception.getReason());
        assertEquals("User is blocked", exception.getMessage());
        verify(userRepository).findByUserName(username);
    }

    @Test
    void shouldPropagateValidationException_whenValidationFails() {
        String username = "";
        String password = "password123";
        
        ValidationException exception = assertThrows(ValidationException.class,
                () -> authenticationService.authenticate(username, password));
        
        assertEquals("Username cannot be empty", exception.getMessage());
        assertEquals("username", exception.getField());
        verify(userRepository, never()).findByUserName(any());
    }

    @Test
    void shouldAuthenticateAdminUser_whenCredentialsAreValid() {
        String username = "admin";
        String password = "adminpass";
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        User admin = new User(1L, username, hashedPassword, "admin@example.com", Role.ADMIN);
        
        when(userRepository.findByUserName(username)).thenReturn(Optional.of(admin));
        
        User result = authenticationService.authenticate(username, password);
        
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(Role.ADMIN, result.getRole());
        verify(userRepository).findByUserName(username);
    }
}
