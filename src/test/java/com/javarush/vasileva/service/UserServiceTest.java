package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = User.builder()
                .id(1L)
                .login("testuser")
                .email("test@gmail.com")
                .password("password123")
                .role(Role.USER)
                .build();
    }

    @Test
    @DisplayName("given user when create then repository create called")
    void givenUser_whenCreate_ThenRepositoryCreateCalled() {
        userRepository.create(testUser);
        verify(userRepository, times(1)).create(testUser);
    }

    @Test
    @DisplayName("given user when update then repository update called")
    void givenUser_whenUpdate_ThenRepositoryUpdateCalled() {
        userRepository.update(testUser);
        verify(userRepository, times(1)).update(testUser);
    }

    @Test
    @DisplayName("given user when delete then repository delete called")
    void givenUser_whenDelete_ThenRepositoryDeleteCalled() {
        userRepository.delete(testUser);
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    @DisplayName("given users in repo when get all then return list")
    void givenUsersInRepo_whenGetAll_thenReturnList() {
        List<User> users = Collections.singletonList(testUser);
        when(userRepository.getAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertEquals(1, result.size());
        assertEquals(testUser.getId(), result.get(0).getId());
        verify(userRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("given user id exists when find by id then return user")
    void givenUserIdExists_WhenFindById_ThenReturnUser() {
        long userId = testUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(testUser.getId(), result.get().getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("given user id not found when find by id then return empty")
    void givenUserIdNotFound_WhenFindById_ThenReturnEmpty() {
        long absentUserId = -1L;
        when(userRepository.findById(absentUserId)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(absentUserId);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(absentUserId);
    }

    @Test
    @DisplayName("given login email password when register then repository create user with user role")
    void givenLoginEmailPassword_WhenRegister_ThenRepositoryCreateUser() {
        String regName = "newuser";
        String regEmail = "new@email.com";
        String regPassword = "password123";
        userService.register(regName, regEmail, regPassword);

        User expectedUser = User.builder()
                .login(regName)
                .email(regEmail)
                .password(regPassword)
                .role(Role.USER)
                .build();

        assertEquals(expectedUser.getRole(), Role.USER);
        verify(userRepository, times(1)).create(expectedUser);
    }

    @Test
    @DisplayName("given valid email and password when login then return user")
    void givenValidEmailAndPassword_WhenLogin_ThenReturnUser() {
        String validEmail = "test@gmail.com";
        String validPassword = "password123";
        when(userRepository.findByEmail(validEmail)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.login(validEmail, validPassword);

        assertTrue(result.isPresent());
        assertEquals(testUser.getId(), result.get().getId());
        verify(userRepository, times(1)).findByEmail(validEmail);
    }

    @Test
    @DisplayName("given wrong password when login then return empty")
    void givenWrongPassword_WhenLogin_ThenReturnEmpty() {
        String validEmail = "test@gmail.com";
        String invalidPassword = "password";
        when(userRepository.findByEmail(validEmail)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.login(validEmail, invalidPassword);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(validEmail);
    }

    @Test
    @DisplayName("given wrong email when login then return empty")
    void givenWrongLogin_WhenLogin_ThenReturnEmpty() {
        String invalidEmail = "test2@gmail.com";
        String validPassword = "password123";
        when(userRepository.findByEmail(invalidEmail)).thenReturn(Optional.empty());

        Optional<User> result = userService.login(invalidEmail, validPassword);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(invalidEmail);
    }

    @Test
    @DisplayName("given valid user id string when get validated user then return user")
    void givenValidUserIdString_WhenGetValidatedUser_ThenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.getValidatedUser("1");

        assertTrue(result.isPresent());
        assertEquals(testUser.getId(), result.get().getId());
    }

    @Test
    @DisplayName("given empty user id string when get validated user then return empty")
    void givenEmptyUserIdString_whenGetValidatedUser_thenReturnEmpty() {
        Optional<User> result = userService.getValidatedUser("");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("given null user when get validated user then return empty")
    void givenNullUserIdString_whenGetValidatedUser_thenReturnEmpty() {
        Optional<User> result = userService.getValidatedUser(null);
        assertFalse(result.isPresent());
    }
}
