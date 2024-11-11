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

@WebServlet("/quest")
public class ConQuestServlet extends HttpServlet {

    private static final QuestService questService = QuestService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer questId = Integer.valueOf(req.getParameter("questId"));

        Optional questOpt = questService.findById(questId);

        if (questOpt.isPresent()) {

            Quest quest = (Quest) questOpt.get();

            req.setAttribute("quest", quest);
            req.getRequestDispatcher("/quest.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Quest not found");
        }


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
