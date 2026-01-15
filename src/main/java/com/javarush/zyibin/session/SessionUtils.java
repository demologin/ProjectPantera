package com.javarush.zyibin.session;

import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.state.InterviewState;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SessionUtils {

    public static final String INTERVIEW_STATE = "interviewState";

    public static boolean hasInterview(HttpSession session) {
        return session != null && session.getAttribute(INTERVIEW_STATE) != null;
    }
    public static InterviewState getInterviewState(HttpSession session) {
        return  (InterviewState) session.getAttribute(INTERVIEW_STATE);
    }

    public static void setInterviewState(HttpSession session, InterviewState state) {
        session.setAttribute(INTERVIEW_STATE, state);
    }
    public static void clearInterview(HttpSession session) {
        session.removeAttribute(INTERVIEW_STATE);
    }
}
