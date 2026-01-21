package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/profile/avatar/upload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 5 * 1024 * 1024,  // 5MB
        maxRequestSize = 6 * 1024 * 1024 // 6MB
)
public class AvatarUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "/uploads/avatars";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        User user = (User) session.getAttribute("currentUser");

        Part filePart = req.getPart("avatar");
        if (filePart == null || filePart.getSize() == 0) {
            resp.sendRedirect(req.getContextPath() + "/profile/avatar");
            return;
        }
        String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String extension = submittedFileName.substring(submittedFileName.lastIndexOf('.'));
        String fileName = UUID.randomUUID() + extension;
        String uploadPath = getServletContext().getRealPath(UPLOAD_DIR);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File file = new File(uploadDir, fileName);
        filePart.write(file.getAbsolutePath());

        user.setAvatarPath(UPLOAD_DIR + "/" + fileName);
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}
