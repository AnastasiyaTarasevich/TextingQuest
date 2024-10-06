package org.example.textingquest.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.services.UserService;
import org.example.textingquest.utils.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {

    private static final UserService userService=UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("nickname"),req.getParameter("password"))
                .ifPresentOrElse(userDTO -> onLoginSuccess(userDTO,req,resp),
                        ()->  onLoginFail(req,resp));
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login?error&nickname="+req.getParameter("nickname"));
    }

    @SneakyThrows
    private void onLoginSuccess(UserDTO userDTO, HttpServletRequest req, HttpServletResponse resp) {
    req.getSession().setAttribute("user",userDTO);
    resp.sendRedirect("/quests");
    }
}
