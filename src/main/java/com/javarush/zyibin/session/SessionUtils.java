package com.javarush.zyibin.session;

import com.javarush.zyibin.model.Question;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SessionUtils {

    public static boolean isInterviewInitialized(HttpSession session) {
        if (session == null) {
            return false;
        }
        return session.getAttribute(SessionKeys.QUESTIONS) != null &&
                session.getAttribute(SessionKeys.CURRENT_INDEX) != null &&
                session.getAttribute(SessionKeys.SCORE) != null;
    }

    @SuppressWarnings("unchecked")
    public static List<Question> getQuestions(HttpSession session) {
        return (List<Question>)  session.getAttribute(SessionKeys.QUESTIONS);
    }

    public static int getCurrentIndex(HttpSession session) {
        return  (Integer) session.getAttribute(SessionKeys.CURRENT_INDEX);
    }
    public static  int getScore(HttpSession session) {
        return (Integer) session.getAttribute(SessionKeys.SCORE);
    }
    public static void setCurrentIndex(HttpSession session, int index) {
        session.setAttribute(SessionKeys.CURRENT_INDEX, index);
    }
    public static void setScore(HttpSession session, int score) {
        session.setAttribute(SessionKeys.SCORE, score);
    }
}
