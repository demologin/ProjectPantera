package com.javarush.lesson16;

import com.javarush.khmelov.config.NanoSpring;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.forlesson16.TxService;

public class MyApp {
    public static void main(String[] args) {
        TxService txService = NanoSpring.find(TxService.class);
        User user = txService.getById(1L);
        System.out.println(user);
    }
}
