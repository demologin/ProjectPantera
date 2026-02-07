package com.javarush.bekk.cmd;

import com.javarush.bekk.entity.Answer;
import com.javarush.bekk.entity.Question;
import com.javarush.bekk.repository.QuestionRepository;
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
        //Object answer = session.getAttribute("answer");
        Question question = questionRepository.get(2L);
        session.setAttribute("question", question);
        return getView();
    }
}
