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

import static com.javarush.vasileva.service.TestData.*;
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
        testUser = createValidUser();
    }

    @Test
    @DisplayName("given user when create then repository create called")
    void givenUser_whenCreate_ThenRepositoryCreateCalled() {
        userService.create(testUser);
        verify(userRepository, times(1)).create(testUser);
    }

    @Test
    @DisplayName("given user when update then repository update called")
    void givenUser_whenUpdate_ThenRepositoryUpdateCalled() {
        userService.update(testUser);
        verify(userRepository, times(1)).update(testUser);
    }

    @Test
    @DisplayName("given user when delete then repository delete called")
    void givenUser_whenDelete_ThenRepositoryDeleteCalled() {
        userService.delete(testUser);
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
        long absentUserId = NON_EXISTENT_USER_ID;
        when(userRepository.findById(absentUserId)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(absentUserId);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(absentUserId);
    }

    @Test
    @DisplayName("given login email password when register then repository create user with user role")
    void givenLoginEmailPassword_WhenRegister_ThenRepositoryCreateUser() {
        userService.register(VALID_USER_LOGIN, VALID_USER_EMAIL, VALID_USER_PASSWORD);

        User expectedUser = User.builder()
                .login(VALID_USER_LOGIN)
                .email(VALID_USER_EMAIL)
                .password(VALID_USER_PASSWORD)
                .role(Role.USER)
                .build();

        assertEquals(expectedUser.getRole(), Role.USER);
        verify(userRepository, times(1)).create(expectedUser);
    }

    @Test
    @DisplayName("given valid email and password when login then return user")
    void givenValidEmailAndPassword_WhenLogin_ThenReturnUser() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.login(testUser.getEmail(), testUser.getPassword());

        assertTrue(result.isPresent());
        assertEquals(testUser.getId(), result.get().getId());
        verify(userRepository, times(1)).findByEmail(testUser.getEmail());
    }

    @Test
    @DisplayName("given wrong password when login then return empty")
    void givenWrongPassword_WhenLogin_ThenReturnEmpty() {
        when(userRepository.findByEmail(VALID_USER_EMAIL)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.login(VALID_USER_EMAIL, INVALID_USER_PASSWORD);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(VALID_USER_EMAIL);
    }

    @Test
    @DisplayName("given wrong email when login then return empty")
    void givenWrongLogin_WhenLogin_ThenReturnEmpty() {
        when(userRepository.findByEmail(INVALID_USER_EMAIL)).thenReturn(Optional.empty());

        Optional<User> result = userService.login(INVALID_USER_EMAIL, VALID_USER_PASSWORD);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(INVALID_USER_EMAIL);
    }

    @Test
    @DisplayName("given valid user id string when get validated user then return user")
    void givenValidUserIdString_WhenGetValidatedUser_ThenReturnUser() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.getValidatedUser(String.valueOf(testUser.getId()));

        assertTrue(result.isPresent());
        assertEquals(testUser.getId(), result.get().getId());
    }

    @Test
    @DisplayName("given empty user id string when get validated user then return empty")
    void givenEmptyUserIdString_whenGetValidatedUser_thenReturnEmpty() {
        Optional<User> result = userService.getValidatedUser(EMPTY_USER_ID_STR);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("given null user when get validated user then return empty")
    void givenNullUserIdString_whenGetValidatedUser_thenReturnEmpty() {
        Optional<User> result = userService.getValidatedUser(NULL_USER_ID_STR);
        assertFalse(result.isPresent());
    }
}
