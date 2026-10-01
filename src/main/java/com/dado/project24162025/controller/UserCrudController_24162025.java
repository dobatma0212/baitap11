package com.dado.project24162025.controller;

import com.dado.project24162025.model.Users_24162025;
import com.dado.project24162025.service.IUserService_24162025;
import com.dado.project24162025.service.UserService_24162025;
import com.dado.project24162025.util.PasswordUtil_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserCrudController_24162025 extends HttpServlet {

    private final IUserService_24162025 userService = new UserService_24162025();
    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                req.getRequestDispatcher("/views/admin/userForm_24162025.jsp").forward(req, resp);
                break;
            case "edit":
                int editId = Integer.parseInt(req.getParameter("id"));
                Users_24162025 u = userService.getUserById(editId);
                req.setAttribute("user", u);
                req.getRequestDispatcher("/views/admin/userForm_24162025.jsp").forward(req, resp);
                break;
            case "delete":
                int delId = Integer.parseInt(req.getParameter("id"));
                userService.deleteUser(delId);
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                break;
            default:
                listUsers(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("save".equals(action)) {
            String idParam = req.getParameter("userId");
            Users_24162025 u = new Users_24162025();
            u.setUsername(req.getParameter("username"));
            u.setEmail(req.getParameter("email"));
            u.setFullname(req.getParameter("fullname"));
            u.setPhone(req.getParameter("phone"));
            u.setStatus(Integer.parseInt(req.getParameter("status")));
            u.setRoleId(Integer.parseInt(req.getParameter("roleId")));
            String sellerIdParam = req.getParameter("sellerid");
            u.setSellerid((sellerIdParam == null || sellerIdParam.trim().isEmpty()) ? null : Integer.parseInt(sellerIdParam));

            if (idParam == null || idParam.trim().isEmpty()) {

                u.setUserId(0);
                u.setPassword(PasswordUtil_24162025.hash("123456"));
                u.setImages("");
                u.setCode(null);

                com.dado.project24162025.dao.IUserDAO_24162025 dao = new com.dado.project24162025.dao.UserDAO_24162025();
                dao.insert(u);
            } else {
                u.setUserId(Integer.parseInt(idParam));
                userService.updateUser(u);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }
        doGet(req, resp);
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        try {
            if (req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException ignored) {}
        if (page < 1) page = 1;

        int totalPages = Math.max(1, userService.totalPages(PAGE_SIZE));
        if (page > totalPages) page = totalPages;

        List<Users_24162025> users = userService.getUsersPaged(page, PAGE_SIZE);

        req.setAttribute("users", users);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/views/admin/userList_24162025.jsp").forward(req, resp);
    }
}
