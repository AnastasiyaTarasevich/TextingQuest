package org.example.textingquest.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.textingquest.dtos.ReviewDTO;
import org.example.textingquest.services.ReviewService;

import java.io.IOException;
import java.util.List;

@WebServlet("/reviews")
public class AllReviewsServlet extends HttpServlet {

    private final ReviewService reviewService=ReviewService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ReviewDTO> reviews = reviewService.getAllReviews(); // Получаем список отзывов
        req.setAttribute("reviews", reviews);
        req.getRequestDispatcher("reviews.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
