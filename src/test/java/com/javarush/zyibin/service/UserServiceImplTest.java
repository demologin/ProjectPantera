package com.javarush.zyibin.service;

import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldRegisterRegularUser() {

        when(userRepository.findByUserName("john"))
                .thenReturn(Optional.empty());

        User user = userService.register(
                "john",
                "password123",
                "john@mail.com"
        );

        assertEquals("john", user.getUsername());
        assertEquals("john@mail.com", user.getEmail());
        assertEquals(Role.USER, user.getRole());

        assertNotEquals(
                "password123",
                user.getPasswordHash(),
                "Пароль должен храниться в захешированном виде"
        );

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldRegisterAdminUserWhenUsernameIsAdmin() {

        when(userRepository.findByUserName("admin"))
                .thenReturn(Optional.empty());

        User user = userService.register(
                "admin",
                "adminPass",
                "admin@mail.com"
        );

        assertEquals(Role.ADMIN, user.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {

        when(userRepository.findByUserName("john"))
                .thenReturn(Optional.of(mock(User.class)));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> userService.register(
                        "john",
                        "password123",
                        "john@mail.com"
                )
        );

        assertEquals(
                "User with this login already exists",
                exception.getMessage()
        );

        verify(userRepository, never()).save(any());
    }
}
