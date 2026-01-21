package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Answer;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.entity.Question;
import com.javarush.vasileva.service.AnswerService;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public class PlayGame implements Command {
    private final QuestService questService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public PlayGame(QuestService questService, QuestionService questionService, AnswerService answerService) {
        this.questService = questService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        String strId = req.getParameter("id");
        if (strId != null && !strId.isEmpty()) {
            long id = Long.parseLong(strId);
            Quest quest = questService.get(id).orElseThrow();
            req.setAttribute("quest", quest);

            System.out.println(quest.getStartQuestionId());
            Optional<Question> current = questionService.get(quest.getStartQuestionId());
            current.ifPresent(question -> req.setAttribute("question", question));
            List<Answer> answers = current.get().getAnswers();
            req.setAttribute("answers", answers);
        }
        return getView();
    }
}
