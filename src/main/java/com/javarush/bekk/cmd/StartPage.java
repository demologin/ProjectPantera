package com.javarush.bekk.cmd;

import com.javarush.bekk.entity.Answer;
import com.javarush.bekk.entity.Question;
import com.javarush.bekk.repository.QuestionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.util.Collection;

@SuppressWarnings("unused")
@AllArgsConstructor
public class StartPage implements Command {

    private final QuestionRepository questionRepository;

    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Question question = questionRepository.get(1L);
        session.setAttribute("question", question);
        return getView();
    }
}
