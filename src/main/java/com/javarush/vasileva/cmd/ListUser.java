package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Key;
import com.javarush.vasileva.util.RequestHelpers;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

import static com.javarush.vasileva.util.Key.USER;

@SuppressWarnings("unused")
public class ListUser implements Command {

    private final UserService userService;

    public ListUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        RequestHelpers.checkAuthorization(request, "Получить список пользователей могут только пользователи с правами ADMIN");
        Collection<User> users = userService.getAll();
        request.setAttribute("users", users);
        request.getSession().setAttribute("ADMIN_ROLE", Role.ADMIN);
        return getView();
    }

    @Override
    public String doDelete(HttpServletRequest req) {
        String userIdStr = req.getParameter(Key.USER_ID);
        if (userIdStr == null) {
            throw new IllegalArgumentException("User ID is not found");
        }

        User user = userService.findById(userIdStr)
                .orElseThrow(() -> new AppException("User is not found: id=" + userIdStr));

        req.setAttribute(USER, user);
        userService.delete(user);
        return getView();
    }


}