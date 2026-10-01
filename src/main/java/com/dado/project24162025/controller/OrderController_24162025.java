package com.dado.project24162025.controller;

import com.dado.project24162025.model.Order_24162025;
import com.dado.project24162025.model.Users_24162025;
import com.dado.project24162025.service.IOrderService_24162025;
import com.dado.project24162025.service.OrderService_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Đơn hàng của User (phân quyền bởi UserAuthFilter_24162025).
 *
 * GET /orders          : danh sách đơn hàng của tôi
 * GET /orders?id=...   : chi tiết một đơn hàng
 */
public class OrderController_24162025 extends HttpServlet {

    private final IOrderService_24162025 orderService = new OrderService_24162025();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162025 user = (Users_24162025) req.getSession().getAttribute("currentUser");
        String id = req.getParameter("id");

        if (id != null && !id.isBlank()) {
            Order_24162025 order = orderService.getOrderDetail(user.getUserId(), id);
            if (order == null) {
                req.getSession().setAttribute("flashError", "Không tìm thấy đơn hàng.");
                resp.sendRedirect(req.getContextPath() + "/orders");
                return;
            }
            req.setAttribute("order", order);
            req.getRequestDispatcher("/views/orderDetail_24162025.jsp").forward(req, resp);
            return;
        }

        List<Order_24162025> orders = orderService.getOrders(user.getUserId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/orderList_24162025.jsp").forward(req, resp);
    }
}
