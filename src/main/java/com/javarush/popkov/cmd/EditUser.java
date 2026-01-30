package com.javarush.popkov.cmd;

import com.javarush.popkov.entity.Gender;
import com.javarush.popkov.entity.Role;
import com.javarush.popkov.entity.User;
import com.javarush.popkov.service.ImageService;
import com.javarush.popkov.service.UserService;
import com.javarush.popkov.util.Go;
import com.javarush.popkov.util.Key;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

@SuppressWarnings("unused")
public class EditUser implements Command {

    private final UserService userService;
    private final ImageService imageService;

    public EditUser(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }


    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter(Key.ID);
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            userService.get(id)
                    .ifPresent(user -> req.setAttribute(Key.USER, user));
        }
        return getView();
    }

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter(Key.ID));
        User user = User.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .role(Role.valueOf(req.getParameter("role")))
                .gender(Gender.valueOf(req.getParameter("gender")))
                .build();
        if (req.getParameter("create") != null) {
            userService.create(user);
            String imageId = "image-" + user.getId();
            if (imageService.uploadImage(req, imageId)) {
                user.setImageId(imageId);
                userService.update(user);
            }
        } else if (req.getParameter("update") != null) {
            user.setId(Long.parseLong(req.getParameter("id")));
            String imageId = "image-" + user.getId();
            if (imageService.uploadImage(req, imageId)) {
                user.setImageId(imageId);
            }
            userService.update(user);
        }
        return Go.EDIT_USER + "?id=" + user.getId();
    }


}