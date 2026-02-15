package com.javarush.goncharov.service;

import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.Repository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    Repository<User> repository;
    @InjectMocks
    UserService userService;

    User user1;
    User user2;
    Map<Long, User> getAllMap;

    final Long TEST_USER_USER = 2L;

    @BeforeEach
    void setup() {
        user1 = User.builder().id(null).login("Dima").password("123").build();
        user2 = User.builder().id(null).login("Dima").password("123").build();
        getAllMap =  Map.of(1L, user1, 2L, user2);
    }

    @Test
    @DisplayName("Create user")
    void createUser() {
        User createdUser = User.builder().id(TEST_USER_USER).login("Dima").password("123").build();
        when(repository.create(user1)).thenReturn(Optional.of(createdUser));

        userService.post(user1);

        assertEquals(TEST_USER_USER, createdUser.getId());
        verify(repository).create(user1);
    }

    @Test
    @DisplayName("Get all users")
    void getAllUsers() {
        when(repository.getAll()).thenReturn(getAllMap);

        userService.getAll();

        assertEquals(2, getAllMap.size());
    }

    @Test
    @DisplayName("Update user")
    void updateUser() {
        User updatesUser = User.builder().id(124L).login("DimaNew").password("123").build();
        when(repository.update(user1)).thenReturn(Optional.of(updatesUser));

        userService.update(user1);

        assertEquals(124L, updatesUser.getId());
    }

    @Test
    @DisplayName("Delete user")
    void deleteUser() {
        Map<Long, User> getAllMapAfterDelete =  Map.of(1L, user1);
        when(repository.delete(user1)).thenReturn(TRUE);

        userService.delete(user1);

        assertEquals(1, getAllMapAfterDelete.size());
    }

    @Test
    @DisplayName("Get one user")
    void getOneUser() {
        User getUser = User.builder().id(1111L).login("Nick").password("123").build();
        when(repository.get(1111L)).thenReturn(Optional.of(getUser));

        userService.get(1111L);

        assertEquals(1111L, getUser.getId());
    }
}



















