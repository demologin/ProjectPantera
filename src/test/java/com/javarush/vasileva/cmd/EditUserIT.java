package com.javarush.vasileva.cmd;

import com.javarush.vasileva.BaseIT;
import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Key;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static com.javarush.vasileva.util.Link.USER_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class EditUserIT extends BaseIT {

    private final UserService userService = mock(UserService.class);

    private final EditUser editUser = new EditUser(userService);

    @Test
    @DisplayName("When GET request with existing user then set user attribute")
    void whenGetRequestWithExistingUser_ThenSetUserAttribute() {
        String userIdStr = String.valueOf(testUser.getId());
        when(req.getParameter(Key.USER_ID)).thenReturn(userIdStr);
        when(userService.getValidatedUser(userIdStr)).thenReturn(Optional.of(testUser));

        String view = editUser.doGet(req);

        assertEquals(editUser.getView(), view);
        verify(req).setAttribute(Key.USER, testUser);
        verify(userService).getValidatedUser(userIdStr);
    }

    @Test
    @DisplayName("When GET request but user not found then do not set attribute")
    void whenGetRequestButUserNotFound_ThenDoNotSetAttribute() {
        String userIdStr = "999";
        when(req.getParameter(Key.USER_ID)).thenReturn(userIdStr);
        when(userService.getValidatedUser(userIdStr)).thenReturn(Optional.empty());

        String view = editUser.doGet(req);

        assertEquals(editUser.getView(), view);
        verify(req, never()).setAttribute(eq(Key.USER), any());
        verify(userService).getValidatedUser(userIdStr);
    }

    @Test
    @DisplayName("When POST request to create user then call create and redirect")
    void whenPostRequestToCreateUser_ThenCallCreateAndRedirect() {
        when(req.getParameter(Key.CREATE)).thenReturn("true");
        when(req.getParameter(Key.LOGIN)).thenReturn(testUser.getLogin());
        when(req.getParameter(Key.EMAIL)).thenReturn(testUser.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(testUser.getPassword());
        when(req.getParameter(Key.ROLE)).thenReturn(Role.USER.toString());

        String redirect = editUser.doPost(req);

        assertEquals(USER_LIST, redirect);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).create(userCaptor.capture());

        User createdUser = userCaptor.getValue();
        assertEquals(testUser.getLogin(), createdUser.getLogin());
        assertEquals(testUser.getEmail(), createdUser.getEmail());
        assertEquals(testUser.getPassword(), createdUser.getPassword());
        assertEquals(testUser.getRole(), createdUser.getRole());
        assertNull(createdUser.getId());
    }

    @Test
    @DisplayName("When POST request to update user then call update and redirect")
    void whenPostRequestToUpdateUser_ThenCallUpdateAndRedirect() {
        when(req.getParameter(Key.UPDATE)).thenReturn("true");
        when(req.getParameter(Key.USER_ID)).thenReturn(String.valueOf(testUser.getId()));
        when(req.getParameter(Key.LOGIN)).thenReturn(testUser.getLogin());
        when(req.getParameter(Key.EMAIL)).thenReturn(testUser.getEmail());
        when(req.getParameter(Key.PASSWORD)).thenReturn(testUser.getPassword());
        when(req.getParameter(Key.ROLE)).thenReturn(Role.USER.toString());

        String redirect = editUser.doPost(req);

        assertEquals(USER_LIST, redirect);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals(testUser.getId(), updatedUser.getId());
        assertEquals(testUser.getLogin(), updatedUser.getLogin());
        assertEquals(testUser.getEmail(), updatedUser.getEmail());
        assertEquals(testUser.getPassword(), updatedUser.getPassword());
        assertEquals(Role.USER, updatedUser.getRole());
    }

    @Test
    @DisplayName("When POST request without create/update params then do nothing and redirect")
    void whenPostRequestWithoutCreateOrUpdate_ThenDoNothingAndRedirect() {
        when(req.getParameter(Key.CREATE)).thenReturn(null);
        when(req.getParameter(Key.UPDATE)).thenReturn(null);
        when(req.getParameter(Key.ROLE)).thenReturn(Role.USER.toString());

        String redirect = editUser.doPost(req);

        assertEquals(USER_LIST, redirect);
        verify(userService, never()).create(any());
        verify(userService, never()).update(any());
    }

}
