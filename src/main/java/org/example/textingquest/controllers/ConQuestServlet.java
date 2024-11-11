package org.example.textingquest.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.textingquest.daos.QuestDAO;
import org.example.textingquest.dtos.QuestDTO;
import org.example.textingquest.entities.Quest;
import org.example.textingquest.mappers.QuestDTOMapper;
import org.example.textingquest.services.QuestService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/quest/*")
public class ConQuestServlet extends HttpServlet {

    private static final QuestService questService=QuestService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // Получение информации о пути
        if (pathInfo != null && pathInfo.startsWith("/")) {
            String[] pathParts = pathInfo.split("/"); // Разделяем по слешам
            if (pathParts.length > 1) {
                try {
                    Integer questId = Integer.parseInt(pathParts[1]); // Получаем ID квест
                    // Получение квеста из QuestService
                    Optional questOpt = questService.findById(questId); // Используем ваш QuestService

                    if (questOpt.isPresent()) {

                     Quest quest = (Quest) questOpt.get();

                    req.setAttribute("quest", quest); // Устанавливаем атрибут "quest" в запросе
                    req.getRequestDispatcher("/quest.jsp").forward(req, resp); // Перенаправляем на страницу деталей квеста
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Quest not found"); // Если квест не найден
                    }
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quest ID format"); // Обработка ошибки формата
                }
            }
        }
    }




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
