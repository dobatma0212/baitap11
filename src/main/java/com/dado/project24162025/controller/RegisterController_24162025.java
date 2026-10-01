package com.dado.project24162025.controller;

import com.dado.project24162025.service.IUserService_24162025;
import com.dado.project24162025.service.UserService_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterController_24162025 extends HttpServlet {

    private final IUserService_24162025 userService = new UserService_24162025();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/register_24162025.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");

        String error = userService.register(username, email, fullname, password, phone);
        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/views/register_24162025.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("email", email);
        req.getRequestDispatcher("/views/otp_24162025.jsp").forward(req, resp);
    }
}
