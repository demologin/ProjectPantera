package bakhtin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Простой сервлет, который обрабатывает GET запросы
 * и передает управление на JSP страницу
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Подготовка данных для передачи в JSP
        String message = "Привет из сервлета!";
        String userName = "Пользователь";

        // Помещаем данные в request - так JSP сможет их прочитать
        request.setAttribute("message", message);
        request.setAttribute("userName", userName);

        // Forward - передаем управление на JSP страницу
        // URL в браузере останется /hello, но контент будет от JSP
        request.getRequestDispatcher("/WEB-INF/hello.jsp").forward(request, response);
    }
}