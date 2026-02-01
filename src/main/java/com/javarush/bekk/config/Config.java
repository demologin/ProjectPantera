package com.javarush.bekk.config;

import com.javarush.bekk.entity.Role;
import com.javarush.bekk.entity.User;
import com.javarush.bekk.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Config {

    private final UserService userService;

    public void fillEmptyRepository() {
        if (userService.get(1L).isEmpty()){
            User admin = buildUser("Carl", "admin", Role.ADMIN);
            userService.create(admin);
            User alisa = buildUser("Alisa", "qwerty", Role.USER);
            userService.create(alisa);
            User bob = buildUser("Bob", "123", Role.GUEST);
            userService.create(bob);
        }
    }

    private static User buildUser(String name, String password, Role role) {
        return  User.builder()
                .login(name)
                .password(password)
                .role(role)
                .build();
    }

}
