package com.javarush.zyibin.service;

import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.repository.UserRepository;

public class AdminUserService {

    private final UserRepository userRepository;

    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void changeUserRole(long adminId, long targetUserId, Role newRole) {

        userRepository.findById(targetUserId).ifPresent(user -> {
            if (user.getId() == adminId) {
                return;
            }
            user.changeRole(newRole);
        });
    }
}
