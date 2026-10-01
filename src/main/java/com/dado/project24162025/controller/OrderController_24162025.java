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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Đơn hàng của User (phân quyền bởi UserAuthFilter_24162025).
 *
 * GET /orders                : danh sách tất cả đơn hàng
 * GET /orders?status=1..8    : lọc danh sách đơn hàng theo trạng thái
 * GET /orders?id=...         : chi tiết một đơn hàng
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

        // Lọc theo trạng thái đơn hàng (1 đến 8)
        String statusParam = req.getParameter("status");
        Integer filterStatus = null;
        if (statusParam != null && !statusParam.isBlank() && !"all".equalsIgnoreCase(statusParam.trim())) {
            try {
                int st = Integer.parseInt(statusParam.trim());
                if (st >= 1 && st <= 8) {
                    filterStatus = st;
                }
            } catch (NumberFormatException ignored) {}
        }

        List<Order_24162025> orders = orderService.getOrders(user.getUserId(), filterStatus);
        Map<Integer, Integer> rawCounts = orderService.getStatusCounts(user.getUserId());
        int totalOrders = 0;
        for (int cnt : rawCounts.values()) {
            totalOrders += cnt;
        }

        // JSP EL coi số nguyên (như 1, 2, ...) là kiểu Long.
        // Map<Integer, Integer> khi map.get(Long) sẽ trả về null vì Integer.equals(Long) == false.
        // Do đó map đưa vào request cần chứa cả key Long và Integer (và String) để JSP EL truy xuất chính xác:
        Map<Object, Integer> statusCounts = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : rawCounts.entrySet()) {
            int status = entry.getKey();
            int count = entry.getValue();
            statusCounts.put(status, count);
            statusCounts.put((long) status, count);
            statusCounts.put(String.valueOf(status), count);
        }

        req.setAttribute("orders", orders);
        req.setAttribute("selectedStatus", filterStatus);
        req.setAttribute("statusCounts", statusCounts);
        req.setAttribute("totalOrders", totalOrders);
        req.getRequestDispatcher("/views/orderList_24162025.jsp").forward(req, resp);
    }
}
