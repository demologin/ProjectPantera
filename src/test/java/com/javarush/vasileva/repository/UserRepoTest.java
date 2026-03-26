package com.javarush.vasileva.repository;

import com.javarush.vasileva.SessionCreator;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.service.TestData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.javarush.vasileva.service.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepoTest {

    @Mock
    private SessionCreator sessionCreator;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private UserRepo userRepo;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = createValidUser();

        when(sessionCreator.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    @DisplayName("when find by id should return user if user exist")
    void whenFindById_ShouldReturnUser() {
        when(session.find(User.class, VALID_USER_ID)).thenReturn(testUser);

        Optional<User> user = userRepo.findById(VALID_USER_ID);

        assertTrue(user.isPresent());
        assertEquals(testUser, user.get());
        verify(session).find(User.class, VALID_USER_ID);
        verify(transaction).commit();
    }

    @Test
    @DisplayName("when create user should add user")
    void whenCreateUser_ShouldAddUser() {
        userRepo.create(testUser);

        verify(session).persist(testUser);
        verify(transaction).commit();
        verify(transaction, never()).rollback();
    }

    @Test
    @DisplayName("when delete user should delete user")
    void whenCreateUser_ShouldDeleteUser() {
        userRepo.delete(testUser);

        verify(session).remove(testUser);
        verify(transaction).commit();
        verify(transaction, never()).rollback();
    }


}
