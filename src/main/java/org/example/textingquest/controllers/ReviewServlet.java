package org.example.textingquest.controllers;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.textingquest.daos.ReviewDAO;
import org.example.textingquest.dtos.ReviewDTO;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.entities.User;
import org.example.textingquest.exceptions.ValidationException;
import org.example.textingquest.services.ReviewService;

import java.io.IOException;

@WebServlet("/submitReview")
public class ReviewServlet extends HttpServlet {
    private final ReviewDAO reviewDAO=ReviewDAO.getInstance();

    private final ReviewService reviewService=ReviewService.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ratingParam = req.getParameter("rating");
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        int quest_id= (int) req.getSession().getAttribute("questId");

        if (reviewService.existsReviewForQuest(user.getId(), quest_id)) {
            req.setAttribute("error", "Вы уже оставили отзыв на этот квест.");
            req.getRequestDispatcher("endOfQuest.jsp").forward(req, resp);
            return;
        }
        var reviewDTO= ReviewDTO.builder()
                .user_id(user.getId())
                .name(req.getParameter("name"))
                .rate(parseRating(ratingParam))
                .description(req.getParameter("description"))
                .quest_id(quest_id)
                .build();
        System.out.println("Received reviewDTO: " + reviewDTO);

        try {
            reviewService.createReview(reviewDTO);
            req.setAttribute("message","Отзыв был успешно добавлен!");
            req.getRequestDispatcher("endOfQuest.jsp").forward(req,resp);
        }catch (ValidationException e)
        {
            req.setAttribute("errors",e.getErrors());
            req.getRequestDispatcher("endOfQuest.jsp").forward(req,resp);
        }

    }
    private Integer parseRating(String ratingParam) {
        if (ratingParam == null || ratingParam.trim().isEmpty()) {
            return null; // Возвращаем null, если поле пустое
        }

        try {
            return Integer.parseInt(ratingParam); // Преобразование строки в число
        } catch (NumberFormatException e) {
            return null; // Возвращаем null при некорректном значении
        }
    }
}
