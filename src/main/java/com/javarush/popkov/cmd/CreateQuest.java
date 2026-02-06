package com.javarush.popkov.cmd;

import com.javarush.popkov.entity.User;
import com.javarush.popkov.service.QuestService;
import com.javarush.popkov.util.Go;
import com.javarush.popkov.util.Key;
import com.javarush.popkov.util.RequestHelpers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CreateQuest implements Command {

    private final QuestService questService;

    @Override
    public String doPost(HttpServletRequest request) {
        String name = request.getParameter(Key.NAME);
        String text = request.getParameter(Key.TEXT);
        Optional<User> optionalUser = RequestHelpers.getUser(request.getSession());
        optionalUser.ifPresent(user -> questService.create(name, text, user.getId()));
        return Go.HOME;
    }
}