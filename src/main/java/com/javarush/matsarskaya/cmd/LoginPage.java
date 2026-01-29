package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private final UserService userService;

    public LoginPage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        logger.debug("Отображение страницы входа");
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("Обработка POST запроса на вход для пользователя: {}", username);

        try {
            userService.loginUser(username, password);
            HttpSession session = request.getSession();
            logger.info("Успешный вход пользователя: {}", username);
            session.setAttribute("username", username);
            return "/home-page";
        } catch (UserNotFoundException e) {
            logger.warn("Неудачная попытка входа: пользователь {} не найден", username);
            request.setAttribute("error", "Пользователь не найден");
        } catch (InvalidCredentialsException e) {
            logger.warn("Неудачная попытка входа: неверный пароль для пользователя {}", username);
            request.setAttribute("error", "Неверное имя пользователя или пароль");
        } catch (Exception e) {
        logger.error("Неожиданная ошибка при входе пользователя {}: {}", username, e.getMessage(), e);
        request.setAttribute("error", "Произошла ошибка при входе");
    }
        return getView();
    }

    @Override
    public String getView() {
        return "/WEB-INF/login-page.jsp";
    }
}
