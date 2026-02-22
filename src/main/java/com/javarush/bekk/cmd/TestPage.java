package com.javarush.bekk.cmd;

import com.javarush.bekk.entity.Answer;
import com.javarush.bekk.entity.Question;
import com.javarush.bekk.repository.QuestionRepository;
import com.javarush.bekk.util.Constant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class TestPage implements Command {

    private final QuestionRepository questionRepository;


    @Override
    public String doPost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String[] answers = (request.getParameter("answer")).split(",");
        int answer = Integer.parseInt(answers[0]);
        int nextQuestionId = Integer.parseInt(answers[1]);
        if (answer == Constant.RIGHT_ANSWER) {
            Question question = questionRepository.get(nextQuestionId);
            session.setAttribute("question", question);
            return getView();
        } else if (answer == Constant.LAST_RIGHT_ANSWER){
            return "win-page";
        } else {
            return "end-page";
        }
    }
}
