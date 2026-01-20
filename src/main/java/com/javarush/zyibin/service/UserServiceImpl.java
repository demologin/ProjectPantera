package com.javarush.zyibin.service;

import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.concurrent.atomic.AtomicLong;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final AtomicLong idGenerator = new AtomicLong(1);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String username, String rawPassword, String email) {
        userRepository.findByUserName(username)
                .ifPresent(u -> {
                    throw new IllegalStateException("User with this login already exists");
                });
        String passwordHash = hashPassword(rawPassword);
        User user = new User(
                idGenerator.getAndIncrement(),
                username,
                passwordHash,
                email,
                Role.USER
        );

        userRepository.save(user);
        return user;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }
}
