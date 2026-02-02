package com.javarush.bekk.cmd;

import com.javarush.bekk.entity.Answer;
import com.javarush.bekk.entity.Question;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class StartPage implements Command {
    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Question question = new Question();
        question.setId(1L);
        question.setText("Single Responsibility Principle (Принцип единственной ответственности)");
        Answer answer = new Answer();
        answer.setId(1L);
        answer.setText("Каждый класс должен иметь только одну причину для изменения, " +
                "то есть выполнять только одну задачу или отвечать за одну ответственность." +
                " Это повышает сплочённость (cohesion) и упрощает поддержку кода");
        Answer answer1 = new Answer();
        answer1.setId(2L);
        answer1.setText("Каждый класс должен иметь множество причин для изменения, то есть выполнять множество задач");
        session.setAttribute("answer", answer);
        session.setAttribute("answer1", answer1);
        session.setAttribute("question", question);
        return getView();
    }
}
