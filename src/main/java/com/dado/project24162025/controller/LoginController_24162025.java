package com.dado.project24162025.controller;

import com.dado.project24162025.model.Users_24162025;
import com.dado.project24162025.service.CartService_24162025;
import com.dado.project24162025.service.ICartService_24162025;
import com.dado.project24162025.service.IUserService_24162025;
import com.dado.project24162025.service.UserService_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController_24162025 extends HttpServlet {

    private final IUserService_24162025 userService = new UserService_24162025();
    private final ICartService_24162025 cartService = new CartService_24162025();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/login_24162025.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Users_24162025 user = userService.login(username, password);

        if (user == null) {
            req.setAttribute("error", "Sai tai khoan/mat khau hoac tai khoan chua kich hoat.");
            req.getRequestDispatcher("/views/login_24162025.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("currentUser", user);
        if (user.getRoleId() == 3) {
            session.setAttribute("cartCount", cartService.countItems(user.getUserId()));
        }

        if (user.getRoleId() == 1) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
