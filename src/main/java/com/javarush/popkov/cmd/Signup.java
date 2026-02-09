package com.javarush.popkov.cmd;

import com.javarush.popkov.entity.Gender;
import com.javarush.popkov.entity.Role;
import com.javarush.popkov.entity.User;
import com.javarush.popkov.service.ImageService;
import com.javarush.popkov.service.UserService;
import com.javarush.popkov.util.Go;
import com.javarush.popkov.util.Key;
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
        String genderParam = request.getParameter("gender");
        Gender gender = genderParam == null ? Gender.MALE : Gender.valueOf(genderParam);
        User user = User.builder()
                .login(request.getParameter(Key.LOGIN))
                .password(request.getParameter(Key.PASSWORD))
                .role(Role.USER)
                .gender(gender)
                .build();
        userService.create(user);
        String imageId = "image-" + user.getId();
        if (imageService.uploadUserImage(request, imageId)) {
            user.setImageId(imageId);
            userService.update(user);
        }
        HttpSession session = request.getSession();
        session.setAttribute(Key.USER, user);
        return Go.PROFILE;
    }
}
