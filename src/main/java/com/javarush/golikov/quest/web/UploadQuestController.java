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
import java.util.logging.Logger;

@WebServlet("/upload")
@MultipartConfig
public class UploadQuestController extends AbstractAdminController {

    private static final Logger log =
            Logger.getLogger(UploadQuestController.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        if (checkAdmin(req, resp)) {
            return;
        }

        Part file = req.getPart("file");
        String id = req.getParameter("id");

        try (InputStream is = file.getInputStream()) {

            adminService.loadQuestFromTxt(id, is);

            resp.sendRedirect(req.getContextPath() + "/admin-quests");

        } catch (Exception e) {

            log.severe("Ошибка загрузки квеста '" + id + "': " + e.getMessage());

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Load error");
        }
    }
}