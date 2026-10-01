package com.dado.project24162025.filter;

import com.dado.project24162025.model.Users_24162025;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Chỉ cho phép vai trò User (roleId = 3) truy cập các chức năng mua hàng (giỏ hàng). */
public class UserAuthFilter_24162025 implements Filter {

    private static final int ROLE_USER = 3;

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        Users_24162025 currentUser = (session != null) ? (Users_24162025) session.getAttribute("currentUser") : null;

        if (currentUser == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (currentUser.getRoleId() != ROLE_USER) {
            req.setAttribute("errorMessage", "Giỏ hàng chỉ dành cho tài khoản User.");
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            req.getRequestDispatcher("/views/error_24162025.jsp").forward(req, res);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
