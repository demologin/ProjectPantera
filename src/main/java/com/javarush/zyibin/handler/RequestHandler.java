package com.javarush.zyibin.handler;

import com.javarush.zyibin.exception.AuthenticationException;
import com.javarush.zyibin.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Универсальный обработчик запросов с ошибками
 * Упрощает обработку исключений в сервлетах
 */
public class RequestHandler {
    
    private final ErrorHandler errorHandler;
    
    public RequestHandler() {
        this.errorHandler = new ErrorHandler();
    }
    
    /**
     * Выполняет запрос с автоматической обработкой ошибок
     * @param request HTTP запрос
     * @param response HTTP ответ
     * @param action действие для выполнения
     * @param errorPage страница для ошибок валидации
     */
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, 
                             RequestAction action, String errorPage) throws IOException {
        try {
            action.execute();
        } catch (ValidationException e) {
            errorHandler.handleValidationError(request, response, e, errorPage);
        } catch (AuthenticationException e) {
            errorHandler.handleAuthenticationError(request, response, e);
        } catch (Exception e) {
            errorHandler.handleGeneralError(request, response, e);
        }
    }
    
    @FunctionalInterface
    public interface RequestAction {
        void execute() throws Exception;
    }
}
