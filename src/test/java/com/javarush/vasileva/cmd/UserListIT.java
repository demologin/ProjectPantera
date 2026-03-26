package com.javarush.vasileva.cmd;

import com.javarush.vasileva.BaseIT;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.AuthService;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Key;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.javarush.vasileva.util.Key.USER_ID;
import static com.javarush.vasileva.util.Value.USER_LIST_AUTH_ERROR;
import static com.javarush.vasileva.util.Value.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserListIT extends BaseIT {

    private final UserService userService = mock(UserService.class);
    private final AuthService authService = mock(AuthService.class);

    private final UserList userList = new UserList(userService, authService);

    @Test
    @DisplayName("When GET request and admin authorized then retrieve users and set attributes")
    void whenGetRequestAndAdminAuthorized_ThenRetrieveUsersAndSetAttributes() {
        List<User> users = Arrays.asList(testAdmin, testUser, testGuest);
        when(userService.getAll()).thenReturn(users);

        doNothing().when(authService).checkAdminAuthorization(req, USER_LIST_AUTH_ERROR);

        String view = userList.doGet(req);
        assertEquals(userList.getView(), view);
        verify(authService).checkAdminAuthorization(req, USER_LIST_AUTH_ERROR);
        verify(userService).getAll();
        verify(req).setAttribute(Key.USERS, users);
    }

    @Test
    @DisplayName("When GET request but unauthorized then throw AppException")
    void whenGetRequestButUnauthorized_ThenThrowAppException() {
        doThrow(new AppException(USER_LIST_AUTH_ERROR))
                .when(authService)
                .checkAdminAuthorization(req, USER_LIST_AUTH_ERROR);

        AppException exception = assertThrows(AppException.class, () -> userList.doGet(req));

        assertEquals(USER_LIST_AUTH_ERROR, exception.getMessage());
        verify(authService).checkAdminAuthorization(req, USER_LIST_AUTH_ERROR);
        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("When DELETE request and user exists then delete user and return view")
    void whenDeleteRequestAndUserExistsThenDeleteUserAndReturnView() {
        String userIdStr = String.valueOf(testUser.getId());

        when(req.getParameter(USER_ID)).thenReturn(userIdStr);
        when(userService.getValidatedUser(userIdStr)).thenReturn(Optional.of(testUser));

        String view = userList.doDelete(req);

        assertEquals(userList.getView(), view);

        verify(userService).getValidatedUser(userIdStr);
        verify(userService).delete(testUser);
    }

    @Test
    @DisplayName("When DELETE request but user not found then throw AppException")
    void whenDeleteRequestButUserNotFound_ThenThrowAppException() {
        String userIdStr = "999";
        when(req.getParameter(USER_ID)).thenReturn(userIdStr);
        when(userService.getValidatedUser(userIdStr)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> userList.doDelete(req));

        assertEquals(USER_NOT_FOUND + userIdStr, exception.getMessage());

        verify(userService).getValidatedUser(userIdStr);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("When DELETE request with null userId then throw AppException")
    void whenDeleteRequestWithNullUserId_ThenThrowAppException() {
        when(req.getParameter(USER_ID)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> userList.doDelete(req));

        assertTrue(exception.getMessage().startsWith(USER_NOT_FOUND));
        verify(userService).getValidatedUser(null);
        verifyNoMoreInteractions(userService);
    }
}
