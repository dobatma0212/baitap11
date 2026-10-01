package com.dado.project24162025.controller;

import com.dado.project24162025.model.CartItem_24162025;
import com.dado.project24162025.model.Users_24162025;
import com.dado.project24162025.service.CartService_24162025;
import com.dado.project24162025.service.ICartService_24162025;
import com.dado.project24162025.service.IOrderService_24162025;
import com.dado.project24162025.service.OrderResult_24162025;
import com.dado.project24162025.service.OrderService_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Thanh toán khi nhận hàng (COD) - dành cho User (roleId = 3), phân quyền bởi UserAuthFilter_24162025.
 *
 * GET  /checkout : form thông tin giao hàng + tóm tắt giỏ hàng
 * POST /checkout : đặt hàng
 */
public class CheckoutController_24162025 extends HttpServlet {

    private final ICartService_24162025 cartService = new CartService_24162025();
    private final IOrderService_24162025 orderService = new OrderService_24162025();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showForm(req, resp, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");   // họ tên / địa chỉ có dấu tiếng Việt
        Users_24162025 user = (Users_24162025) req.getSession().getAttribute("currentUser");

        OrderResult_24162025 result = orderService.placeCodOrder(
                user.getUserId(),
                req.getParameter("receiverName"),
                req.getParameter("receiverPhone"),
                req.getParameter("shippingAddress"),
                req.getParameter("note"));

        HttpSession session = req.getSession();
        if (result.isOk()) {
            session.setAttribute("cartCount", cartService.countItems(user.getUserId()));
            session.setAttribute("flashMsg", result.getMessage());
            resp.sendRedirect(req.getContextPath() + "/orders?id=" + result.getOrderId());
        } else if (result.isCartProblem()) {
            session.setAttribute("cartCount", cartService.countItems(user.getUserId()));
            session.setAttribute("flashError", result.getMessage());
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else {
            showForm(req, resp, result.getMessage());   // giữ nguyên dữ liệu người dùng đã nhập
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        Users_24162025 user = (Users_24162025) req.getSession().getAttribute("currentUser");
        List<CartItem_24162025> items = cartService.getCartItems(user.getUserId());

        String issue = orderService.checkCartIssue(items);
        if (issue != null) {
            req.getSession().setAttribute("flashError", issue);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", cartService.calcTotal(items));
        req.setAttribute("error", error);

        // Lần đầu vào trang: điền sẵn họ tên / SĐT từ tài khoản. Sau khi lỗi: giữ giá trị vừa nhập.
        if (error == null) {
            req.setAttribute("receiverName", user.getFullname());
            req.setAttribute("receiverPhone", user.getPhone());
        } else {
            req.setAttribute("receiverName", req.getParameter("receiverName"));
            req.setAttribute("receiverPhone", req.getParameter("receiverPhone"));
            req.setAttribute("shippingAddress", req.getParameter("shippingAddress"));
            req.setAttribute("note", req.getParameter("note"));
        }
        req.getRequestDispatcher("/views/checkout_24162025.jsp").forward(req, resp);
    }
}
