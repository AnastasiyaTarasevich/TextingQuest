package org.example.textingquest.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.exceptions.ValidationException;
import org.example.textingquest.services.UserService;
import org.example.textingquest.utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private final UserService userService=UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDTO= UserDTO.builder()
                .nickname(req.getParameter("nickname"))
                .password(req.getParameter("password"))
                .email(req.getParameter("email"))
                .build();
        System.out.println("Received userDTO: " + userDTO);
        try {
            userService.createUser(userDTO);
            resp.sendRedirect("/login");
        }catch (ValidationException e)
        {
            req.setAttribute("errors",e.getErrors());
            doGet(req, resp);
        }
    }
}
