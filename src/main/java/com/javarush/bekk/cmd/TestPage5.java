package com.javarush.bekk.cmd;


import com.javarush.bekk.entity.Question;
import com.javarush.bekk.repository.QuestionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestPage5 implements Command {

    private final QuestionRepository questionRepository;



    @Override
    public String doPost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int answer = Integer.parseInt(request.getParameter("answer"));
        if (answer == 1){
            Question question = questionRepository.get(5L);
            session.setAttribute("question", question);
            return getView();
        } else  {
            return "end-page";
        }
    }
}
