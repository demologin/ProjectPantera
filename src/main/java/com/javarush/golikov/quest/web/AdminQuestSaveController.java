package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.web.admin.AbstractAdminController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/admin-quest-save")
@MultipartConfig
public class AdminQuestSaveController extends AbstractAdminController {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        if (checkAdmin(req, resp)) {
            return;
        }

        Part file = req.getPart("file");
        String id = req.getParameter("id");

        try (InputStream is = file.getInputStream()) {
            adminService.loadQuestFromTxt(id, is);
        }

        resp.sendRedirect(req.getContextPath() + "/admin-quests");
    }
}

