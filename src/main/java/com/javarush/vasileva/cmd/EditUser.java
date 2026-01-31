package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Link;
import com.javarush.vasileva.util.Helpers;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;


@SuppressWarnings("unused")
public class EditUser implements Command {

    private final UserService userService;

    public EditUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter("id");
        Optional<User> optionalUser = userService.findById(stringId);
        optionalUser.ifPresent(user -> req.setAttribute("user", user));
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        User user = User.builder()
                .login(req.getParameter("login"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .role(Role.valueOf(req.getParameter("role")))
                .build();
        if (req.getParameter("create") != null) {
            userService.create(user);
        } else if (req.getParameter("update") != null) {
            user.setId(Helpers.parseStringToLong(req.getParameter("id")));
            userService.update(user);
        }
        return Link.USER_LIST;
    }
}