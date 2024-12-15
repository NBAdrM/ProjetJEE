package com.example.projetjee.controllers;

import com.example.projetjee.models.Student;
import com.example.projetjee.utils.DbConnect;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import static com.example.projetjee.utils.PdfGenerator.generatBulltin;

public class BulletinServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String studentUsername = request.getParameter("studentId");
        String yearStr = request.getParameter("year");

        try {
            int studentId = DbConnect.getUserIdByUsername(studentUsername);
            int year = Integer.parseInt(yearStr);

            generatBulltin(studentId, year);

            Student student = DbConnect.getStudent(studentId);
            String fileName = "BULLTIN_" + student.getFirstName().toUpperCase() + "_" + student.getLastName().toUpperCase() + "_" + year + "_" + (year + 1) + ".pdf";
            String filePath = "src/main/webapp/tmp/" + fileName;

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            File file = new File(filePath);
            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } catch (NumberFormatException | SQLException | ClassNotFoundException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid student ID or year.");
        }
    }
}