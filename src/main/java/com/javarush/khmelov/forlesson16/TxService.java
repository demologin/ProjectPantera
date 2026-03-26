package com.javarush.khmelov.forlesson16;

import com.javarush.khmelov.entity.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class TxService {

    private final InnerService innerService;

    @Transactional
    public User getById(Long id) {
        return innerService.getById(id);
    }
}
