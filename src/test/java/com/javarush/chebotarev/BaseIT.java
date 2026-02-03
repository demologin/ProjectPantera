package com.javarush.chebotarev;

import com.javarush.chebotarev.quest.Quest;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BaseIT extends Base {

    protected final HttpServletRequest req;
    protected final HttpSession session;
    protected final HttpServlet servlet;
    protected final ServletContext servletContext;

    protected BaseIT() {
        req = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        doNothing().when(session).setAttribute(anyString(), any());
        servlet = mock(HttpServlet.class);
        servletContext = mock(ServletContext.class);
        when(servlet.getServletContext()).thenReturn(servletContext);
    }

    protected String createQuestFile() {
        String filepath;
        try (InputStream inputStream = createInputStream()) {
            Quest quest = questService.loadQuest(inputStream);
            filepath = questService.saveQuest(quest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filepath;
    }

    protected void deleteQuestFile(String filepath) {
        File file = new File(filepath);
        if (!file.delete()) {
            throw new RuntimeException(filepath + " not deleted");
        }
    }
}
