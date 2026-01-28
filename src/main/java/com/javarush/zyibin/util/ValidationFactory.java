package com.javarush.zyibin.util;

import com.javarush.zyibin.handler.ErrorHandler;
import com.javarush.zyibin.validation.QuestionValidator;
import com.javarush.zyibin.validation.UserValidation;

public class ValidationFactory {

    private ValidationFactory() {
    }

    public static UserValidation createUserValidator() {
        return new UserValidation();
    }

    public static QuestionValidator createQuestionValidator() {
        return new QuestionValidator();
    }

    public static ErrorHandler createErrorHandler() {
        return new ErrorHandler();
    }
}
