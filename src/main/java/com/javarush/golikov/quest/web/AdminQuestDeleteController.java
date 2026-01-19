package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.web.admin.AbstractAdminController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin-quest-delete")
public class AdminQuestDeleteController extends AbstractAdminController {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if (checkAdmin(req, resp)) {
            return;
        }

        adminService.deleteQuest(req.getParameter("id"));
        resp.sendRedirect(req.getContextPath() + "/admin-quests");
    }
}

