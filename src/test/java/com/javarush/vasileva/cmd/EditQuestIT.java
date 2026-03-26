package com.javarush.vasileva.cmd;

import com.javarush.vasileva.BaseIT;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.mapper.QuestMapper;
import com.javarush.vasileva.service.AuthService;
import com.javarush.vasileva.service.QuestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Link.HOME;
import static com.javarush.vasileva.util.Value.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EditQuestIT extends BaseIT {

    private final QuestService questService = mock(QuestService.class);
    private final AuthService authService = mock(AuthService.class);
    private final QuestMapper questMapper = mock(QuestMapper.class);

    private final EditQuest editQuest = new EditQuest(questService, authService, config);

    @Test
    @DisplayName("when GET request admin authorized no questId then set edit false and default JSON")
    void whenGetNoQuestId_ThenSetEditFalseAndDefaultJson() {
        doNothing().when(authService).checkAdminAuthorization(req, EDIT_QUEST_AUTH_ERROR);
        when(req.getParameter(QUEST_ID)).thenReturn(null);
        when(questService.getAll()).thenReturn(Collections.singletonList(testQuest));

        String view = editQuest.doGet(req);

        assertEquals(editQuest.getView(), view);
        verify(authService).checkAdminAuthorization(req, EDIT_QUEST_AUTH_ERROR);
        verify(req).setAttribute(eq(QUESTS), anyList());
        verify(req).setAttribute(eq(EDIT), eq(false));
        verify(req).setAttribute(eq(QUEST_JSON), eq(JSON_SAMPLE));
    }

    @Test
    @DisplayName("when POST request with null JSON parameter then set error in session")
    void whenPostNullJsonParameterThenSetErrorInSession() {
        when(req.getParameter(QUEST_JSON)).thenReturn(null);

        String view = editQuest.doPost(req);

        assertEquals(editQuest.getView(), view);
        verify(session).setAttribute(eq(ERROR), eq(JSON_SAVE_ERROR));
        verify(session, never()).setAttribute(eq(QUEST_JSON), any());

        verifyNoInteractions(questMapper);
        verifyNoInteractions(questService);
    }

    @Test
    @DisplayName("when JSON serialization fails in doGet then return view")
    void whenJsonSerializationFailsInDoGet_ThenReturnViewAndLogError() throws Exception {
        doNothing().when(authService).checkAdminAuthorization(req, EDIT_QUEST_AUTH_ERROR);

        when(req.getParameter(QUEST_ID)).thenReturn(String.valueOf(testQuest.getId()));
        when(questService.getValidatedQuest(String.valueOf(testQuest.getId())))
                .thenReturn(Optional.of(testQuest));

        when(questMapper.toJsonString(testQuest))
                .thenThrow(new IOException("Serialization failed"));

        String resultView = editQuest.doGet(req);

        assertEquals(editQuest.getView(), resultView);
        verify(req).setAttribute(eq(EDIT), eq(true));
        assertNull(req.getAttribute(QUEST_JSON));
        assertThrows(IOException.class, () -> questMapper.toJsonString(testQuest));
    }

    @Test
    @DisplayName("when GET request but quest not found then throw AppException")
    void whenGetQuestNotFound_ThenThrowAppException() {
        String questIdStr = "999";
        doNothing().when(authService).checkAdminAuthorization(req, EDIT_QUEST_AUTH_ERROR);
        when(req.getParameter(QUEST_ID)).thenReturn(questIdStr);
        when(questService.getValidatedQuest(questIdStr)).thenReturn(Optional.empty());

        when(questService.getAll()).thenReturn(List.of());

        AppException exception = assertThrows(AppException.class, () -> editQuest.doGet(req));

        assertEquals(QUEST_NOT_FOUND + questIdStr, exception.getMessage());
        verify(questService).getValidatedQuest(questIdStr);
    }

    @Test
    @DisplayName("when GET request but serialization fails then set error in session")
    void whenGetSerializationFails_ThenSetErrorInSession() throws Exception {
        doNothing().when(authService).checkAdminAuthorization(req, EDIT_QUEST_AUTH_ERROR);
        when(req.getParameter(QUEST_ID)).thenReturn(String.valueOf(testQuest.getId()));
        when(questService.getValidatedQuest(String.valueOf(testQuest.getId())))
                .thenReturn(Optional.of(testQuest));
        when(questMapper.toJsonString(testQuest)).thenThrow(new IOException("Serialization failed"));
        when(questService.getAll()).thenReturn(Collections.singletonList(testQuest));

        String view = editQuest.doGet(req);

        assertEquals(editQuest.getView(), view);
    }

    @Test
    @DisplayName("when POST request and JSON valid then save quest and redirect to HOME")
    void whenPostValidJson_ThenSaveQuestAndRedirect() throws Exception {
        when(req.getParameter(QUEST_JSON)).thenReturn(testJsonWithoutId);
        when(questMapper.fromJsonString(testJson)).thenReturn(testQuestWithoutId);

        String redirect = editQuest.doPost(req);

        assertEquals(HOME, redirect);
        verify(questService).create(any(Quest.class));

        ArgumentCaptor<Quest> questCaptor = ArgumentCaptor.forClass(Quest.class);
        verify(questService).create(questCaptor.capture());
    }

    @Test
    @DisplayName("when POST request with existing quest ID then update quest")
    void whenPostWithExistingId_ThenUpdateQuest() throws Exception {
        when(req.getParameter(QUEST_JSON)).thenReturn(testJson);
        when(questMapper.fromJsonString(testJson)).thenReturn(testQuest);

        String redirect = editQuest.doPost(req);

        assertEquals(HOME, redirect);
        verify(questService).update(any(Quest.class));

        ArgumentCaptor<Quest> questCaptor = ArgumentCaptor.forClass(Quest.class);
        verify(questService).update(questCaptor.capture());
    }

    @Test
    @DisplayName("when POST request but JSON invalid then set error in session")
    void whenPostInvalidJson_ThenSetErrorInSession() throws Exception {
        String questJson = "invalid json";
        when(req.getParameter(QUEST_JSON)).thenReturn(questJson);
        when(questMapper.fromJsonString(questJson))
                .thenThrow(new IOException("Parse failed"));

        String view = editQuest.doPost(req);

        assertEquals(editQuest.getView(), view);

        verify(session).setAttribute(eq(QUEST_JSON), eq(questJson));
        verify(session).setAttribute(eq(ERROR), eq(JSON_SAVE_ERROR));
    }


    @Test
    @DisplayName("When GET request but admin authorization fails then throw AppException")
    void whenGetAdminAuthorizationFails_ThenThrowAppException() {
        doThrow(new AppException(EDIT_QUEST_AUTH_ERROR))
                .when(authService)
                .checkAdminAuthorization(req, EDIT_QUEST_AUTH_ERROR);

        AppException exception = assertThrows(AppException.class, () -> editQuest.doGet(req));

        assertEquals(EDIT_QUEST_AUTH_ERROR, exception.getMessage());
        verify(authService).checkAdminAuthorization(req, EDIT_QUEST_AUTH_ERROR);
        verifyNoInteractions(questService);
        verifyNoInteractions(req);
    }
}
