package com.javarush.zyibin.service;

import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldRegisterUser_whenUsernameIsFree() {

        when(userRepository.findByUserName("john"))
                .thenReturn(Optional.empty());

        User user = userService.register(
                "john",
                "password123",
                "john@mail.com"
        );

        assertNotNull(user);
        assertEquals("john", user.getUsername());
        assertEquals("john@mail.com", user.getEmail());

        assertNotEquals("password123", user.getPasswordHash());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowException_whenUsernameAlreadyExists() {

        when(userRepository.findByUserName("john"))
                .thenReturn(Optional.of(mock(User.class)));

        assertThrows(
                IllegalStateException.class,
                () -> userService.register(
                        "john",
                        "password123",
                        "john@mail.com"
                )
        );

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldSaveUserWithHashedPassword() {

        when(userRepository.findByUserName("john"))
                .thenReturn(Optional.empty());

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        userService.register(
                "john",
                "password123",
                "john@mail.com"
        );

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertNotEquals("password123", savedUser.getPasswordHash());
        assertFalse(savedUser.getPasswordHash().isBlank());
    }
}
