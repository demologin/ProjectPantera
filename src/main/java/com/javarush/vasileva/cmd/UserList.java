package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.RequestHelpers;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class UserList implements Command {

    private final UserService userService;

    public UserList(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        RequestHelpers.checkAuthorization(request, USER_LIST_AUTH_ERROR);
        Collection<User> users = userService.getAll();
        request.setAttribute("users", users);
        request.getSession().setAttribute("ADMIN_ROLE", Role.ADMIN);
        return getView();
    }

    @Override
    public String doDelete(HttpServletRequest req) {
        String userIdStr = req.getParameter(USER_ID);
        User user = userService.findById(userIdStr)
                .orElseThrow(() -> new AppException(USER_NOT_FOUND + userIdStr));
        req.setAttribute(USER, user);
        userService.delete(user);
        return getView();
    }
}