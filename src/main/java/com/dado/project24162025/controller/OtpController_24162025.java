package com.dado.project24162025.controller;

import com.dado.project24162025.service.IUserService_24162025;
import com.dado.project24162025.service.UserService_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OtpController_24162025 extends HttpServlet {

    private final IUserService_24162025 userService = new UserService_24162025();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String otp = req.getParameter("otp");

        boolean ok = userService.verifyOtp(email, otp);
        if (ok) {
            req.setAttribute("message", "Kich hoat tai khoan thanh cong! Vui long dang nhap.");
            req.getRequestDispatcher("/views/login_24162025.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Ma OTP khong dung, vui long thu lai.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/otp_24162025.jsp").forward(req, resp);
        }
    }
}
