package bakhtin;

import bakhtin.exсeptions.IllegalActionException;
import bakhtin.exсeptions.NoActiveQuestException;
import bakhtin.exсeptions.NoAnswerGivenException;
import bakhtin.exсeptions.NoQuestionException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class ErrorHandlingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(req, resp);
        } catch (NoActiveQuestException e) {
            // TODO(log): log.error("No active quest exception", e);
            req.setAttribute("errorMessage", "Квест не найден. Начните квест заново.");
            req.getRequestDispatcher("/WEB-INF/hello.jsp").forward(req, resp);
        } catch (NoAnswerGivenException e) {
            // TODO(log): log.error("No answer given exception", e);
            req.setAttribute("errorMessage", "Не выбран ответ. Попробуйте ещё раз.");
            req.getRequestDispatcher("/WEB-INF/hello.jsp").forward(req, resp);
        } catch (NoQuestionException e) {
            // TODO(log): log.error("No question exception", e);
            req.setAttribute("errorMessage", "Вопрос не найден. Начните квест заново.");
            req.getRequestDispatcher("/WEB-INF/hello.jsp").forward(req, resp);
        } catch (IllegalActionException e) {
            // TODO(log): log.error("Illegal action exception", e);
            req.setAttribute("errorMessage", "Неверное действие. Попробуйте ещё раз");
            req.getRequestDispatcher("/WEB-INF/hello.jsp").forward(req, resp);
        }
    }
}