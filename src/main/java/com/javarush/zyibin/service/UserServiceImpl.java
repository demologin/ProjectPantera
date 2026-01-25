package com.javarush.zyibin.service;

import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String username, String rawPassword, String email) {
        log.info("User registration attempt: username={}", username);
        Role role = username.equals("admin")
                ? Role.ADMIN
                : Role.USER;
        userRepository.findByUserName(username)
                .ifPresent(u -> {
                    log.warn("Registration failed: username {} already exists", username);
                    throw new IllegalStateException("User with this login already exists");
                });
        String passwordHash = hashPassword(rawPassword);
        User user = new User(
                0,
                username,
                passwordHash,
                email,
                role
        );

        userRepository.save(user);
        log.info("User registered successfully: id={}, username={}, role={}",
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
        return user;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            log.error("Password hashing failed", e);
            throw new RuntimeException("Failed to hash password", e);
        }
    }
}
