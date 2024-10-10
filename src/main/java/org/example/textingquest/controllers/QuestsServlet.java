package org.example.textingquest.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.textingquest.daos.QuestDAO;
import org.example.textingquest.entities.Quest;

import java.io.IOException;
import java.util.List;

@WebServlet("/quests")
public class QuestsServlet extends HttpServlet {

    private final QuestDAO questDAO=QuestDAO.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List quests = questDAO.findAll();

        // Добавление списка квестов в атрибуты запроса
        req.setAttribute("quests", quests);

        // Перенаправление на JSP-страницу
        req.getRequestDispatcher("quests.jsp").forward(req, resp);
    }
}
