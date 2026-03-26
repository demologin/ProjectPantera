package com.javarush.vasileva.cmd;

import com.javarush.vasileva.BaseIT;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.AuthService;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.util.Key;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.javarush.vasileva.util.Key.QUEST_ID;
import static com.javarush.vasileva.util.Value.DELETE_QUEST_AUTH_ERROR;
import static com.javarush.vasileva.util.Value.QUEST_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeIT extends BaseIT {

    private final QuestService questService = mock(QuestService.class);
    private final AuthService authService = mock(AuthService.class);

    private final Home home = new Home(questService, authService);

    @Test
    @DisplayName("When GET request then retrieve all quests and set attribute")
    void whenGetRequest_ThenRetrieveAllQuestsAndSetAttribute() {
        List<Quest> quests = Collections.singletonList(testQuest);

        when(questService.getAll()).thenReturn(quests);

        String view = home.doGet(req);

        assertEquals(home.getView(), view);
        verify(req).setAttribute(Key.QUESTS, quests);
        verify(questService).getAll();
    }

    @Test
    @DisplayName("When DELETE request and admin authorized then delete quest and return view")
    void whenDeleteRequestAndAdminAuthorized_ThenDeleteQuestAndReturnView() {
        String questIdStr = String.valueOf(testQuest.getId());
        when(req.getParameter(QUEST_ID)).thenReturn(questIdStr);
        when(questService.getValidatedQuest(questIdStr)).thenReturn(Optional.of(testQuest));
        when(session.getAttribute("role")).thenReturn(Role.ADMIN);

        doNothing().when(authService).checkAdminAuthorization(req, DELETE_QUEST_AUTH_ERROR);

        String view = home.doDelete(req);

        assertEquals(home.getView(), view);
        verify(authService).checkAdminAuthorization(req, DELETE_QUEST_AUTH_ERROR);
        verify(questService).getValidatedQuest(questIdStr);
        verify(questService).delete(testQuest);
        verify(req).setAttribute(Key.QUEST, testQuest);
    }

    @Test
    @DisplayName("When DELETE request but quest not found then throw IllegalArgumentException")
    void whenDeleteRequestButQuestNotFound_ThenThrowIllegalArgumentException() {
        String questIdStr = "999";
        when(req.getParameter(QUEST_ID)).thenReturn(questIdStr);
        when(questService.getValidatedQuest(questIdStr)).thenReturn(Optional.empty());

        doNothing().when(authService).checkAdminAuthorization(req, DELETE_QUEST_AUTH_ERROR);

        AppException exception = assertThrows(AppException.class, () -> home.doDelete(req));

        assertTrue(exception.getMessage().contains(QUEST_NOT_FOUND + questIdStr));
        verify(authService).checkAdminAuthorization(req, DELETE_QUEST_AUTH_ERROR);
        verify(questService).getValidatedQuest(questIdStr);
        verifyNoMoreInteractions(questService);
    }

    @Test
    @DisplayName("When DELETE request but unauthorized then throw AppException")
    void whenDeleteRequestButUnauthorized_ThenThrowAppException() {
        String questIdStr = String.valueOf(testQuest.getId());
        when(req.getParameter(QUEST_ID)).thenReturn(questIdStr);

        doThrow(new AppException(DELETE_QUEST_AUTH_ERROR))
                .when(authService)
                .checkAdminAuthorization(req, DELETE_QUEST_AUTH_ERROR);

        AppException exception = assertThrows(AppException.class, () -> home.doDelete(req));

        assertEquals(DELETE_QUEST_AUTH_ERROR, exception.getMessage());
        verify(authService).checkAdminAuthorization(req, DELETE_QUEST_AUTH_ERROR);
        verifyNoInteractions(questService);
    }
}