package com.javarush.zyibin.session;

import com.javarush.zyibin.state.InterviewState;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionUtilsTest {

    @Test
    void hasInterviewShouldReturnFalseWhenSessionIsNull() {

        boolean result = SessionUtils.hasInterview(null);

        assertFalse(result);
    }

    @Test
    void hasInterviewShouldReturnFalseWhenNoInterviewStateInSession() {

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(SessionUtils.INTERVIEW_STATE))
                .thenReturn(null);

        boolean result = SessionUtils.hasInterview(session);

        assertFalse(result);
    }

    @Test
    void hasInterviewShouldReturnTrueWhenInterviewStateExists() {

        HttpSession session = mock(HttpSession.class);
        InterviewState state = mock(InterviewState.class);

        when(session.getAttribute(SessionUtils.INTERVIEW_STATE))
                .thenReturn(state);

        boolean result = SessionUtils.hasInterview(session);

        assertTrue(result);
    }

    @Test
    void getInterviewStateShouldReturnStateFromSession() {

        HttpSession session = mock(HttpSession.class);
        InterviewState state = mock(InterviewState.class);

        when(session.getAttribute(SessionUtils.INTERVIEW_STATE))
                .thenReturn(state);

        InterviewState result =
                SessionUtils.getInterviewState(session);

        assertEquals(state, result);
    }

    @Test
    void setInterviewStateShouldStoreStateInSession() {

        HttpSession session = mock(HttpSession.class);
        InterviewState state = mock(InterviewState.class);

        SessionUtils.setInterviewState(session, state);

        verify(session, times(1))
                .setAttribute(SessionUtils.INTERVIEW_STATE, state);
    }

    @Test
    void clearInterviewShouldRemoveStateFromSession() {

        HttpSession session = mock(HttpSession.class);

        SessionUtils.clearInterview(session);

        verify(session, times(1))
                .removeAttribute(SessionUtils.INTERVIEW_STATE);
    }
}
