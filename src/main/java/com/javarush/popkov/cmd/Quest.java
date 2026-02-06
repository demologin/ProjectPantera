package com.javarush.popkov.cmd;

import com.javarush.popkov.entity.Question;
import com.javarush.popkov.entity.Role;
import com.javarush.popkov.entity.User;
import com.javarush.popkov.service.ImageService;
import com.javarush.popkov.service.QuestService;
import com.javarush.popkov.service.QuestionService;
import com.javarush.popkov.util.Go;
import com.javarush.popkov.util.Key;
import com.javarush.popkov.util.RequestHelpers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Optional;

import static com.javarush.popkov.util.Key.QUEST;


@AllArgsConstructor
public class Quest implements Command {

    private final QuestService questService;
    private final QuestionService questionService;
    private final ImageService imageService;


    @Override
    public String doGet(HttpServletRequest req) {
        long id = RequestHelpers.getId(req);
        Optional<com.javarush.popkov.entity.Quest> quest = questService.get(id);
        req.setAttribute(QUEST, quest.orElseThrow());
        return getView();
    }

    @Override
    @SneakyThrows
    public String doPost(HttpServletRequest req) {
        Optional<User> editor = RequestHelpers.getUser(req.getSession());
        if (editor.isPresent() && editor.get().getRole() == Role.ADMIN) {
            Long id = RequestHelpers.getId(req);
            Long questionId = RequestHelpers.getId(req, "questionId");
            String text = req.getParameter(Key.TEXT);
            Optional<Question> question = questionService.update(questionId, text);
            if (question.isPresent()) {
                imageService.uploadImage(req, question.get().getImage());
            }
            return "%s?id=%d#bookmark%d".formatted(Go.QUEST, id, questionId);
        } else {
            return Go.QUEST; //TODO добавить ошибку, что "Недостаточно прав для редактирования";
        }
    }
}

