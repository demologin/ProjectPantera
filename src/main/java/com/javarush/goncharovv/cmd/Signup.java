package com.javarush.goncharovv.cmd;

import com.javarush.goncharovv.entity.Role;
import com.javarush.goncharovv.entity.User;
import com.javarush.goncharovv.service.ImageService;
import com.javarush.goncharovv.service.UserService;
import com.javarush.goncharovv.util.Go;
import com.javarush.goncharovv.util.Key;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@SuppressWarnings("unused")
@AllArgsConstructor
public class Signup implements Command {

    private final UserService userService;
    private final ImageService imageService;

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest request) {
        User user = User.builder()
                .login(request.getParameter(Key.LOGIN))
                .password(request.getParameter(Key.PASSWORD))
                .role(Role.USER)
                .build();
        userService.create(user);
        imageService.uploadImage(request, user.getImage());
        HttpSession session = request.getSession();
        session.setAttribute(Key.USER, user);
        return Go.PROFILE;
    }
}
