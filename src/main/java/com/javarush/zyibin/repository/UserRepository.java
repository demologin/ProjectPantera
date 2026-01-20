package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByUserName(String username);

    Optional<User> findById(long id);
}
