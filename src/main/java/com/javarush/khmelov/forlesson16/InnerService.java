package com.javarush.khmelov.forlesson16;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InnerService {

    private final UserRepository userRepository;

    @Transactional
    public User getById(Long id) {
        return userRepository.get(id);
    }
}
