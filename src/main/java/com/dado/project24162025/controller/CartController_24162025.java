package com.dado.project24162025.controller;

import com.dado.project24162025.model.CartItem_24162025;
import com.dado.project24162025.model.Users_24162025;
import com.dado.project24162025.service.CartResult_24162025;
import com.dado.project24162025.service.CartService_24162025;
import com.dado.project24162025.service.ICartService_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Giỏ hàng dành cho vai trò User (roleId = 3).
 * Quyền truy cập được kiểm tra bởi UserAuthFilter_24162025.
 *
 * GET  /cart                       : xem giỏ hàng
 * POST /cart action=add            : thêm sản phẩm   (productId, quantity, from)
 * POST /cart action=update         : sửa số lượng    (cartItemId, quantity | delta)
 * POST /cart action=remove         : xóa 1 dòng      (cartItemId)
 * POST /cart action=clear          : xóa toàn bộ giỏ
 */
public class CartController_24162025 extends HttpServlet {

    private final ICartService_24162025 cartService = new CartService_24162025();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162025 user = currentUser(req);
        List<CartItem_24162025> items = cartService.getCartItems(user.getUserId());

        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", cartService.calcTotal(items));
        refreshCartCount(req, user);
        req.getRequestDispatcher("/views/cart_24162025.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162025 user = currentUser(req);
        String action = req.getParameter("action");
        if (action == null) action = "";

        CartResult_24162025 result;
        String redirectTo = req.getContextPath() + "/cart";

        switch (action) {
            case "add": {
                Integer productId = parseInt(req.getParameter("productId"));
                Integer quantity = parseInt(req.getParameter("quantity"));
                if (quantity == null && req.getParameter("quantity") == null) quantity = 1;

                if (productId == null || quantity == null) {
                    result = CartResult_24162025.fail("Dữ liệu không hợp lệ.");
                } else {
                    result = cartService.addToCart(user.getUserId(), productId, quantity);
                }

                // Quay lại trang người dùng đang xem (chỉ cho phép các giá trị cố định)
                String from = req.getParameter("from");
                if ("detail".equals(from) && productId != null) {
                    redirectTo = req.getContextPath() + "/product-detail?id=" + productId;
                } else if ("list".equals(from)) {
                    redirectTo = req.getContextPath() + "/products";
                }
                break;
            }
            case "update": {
                String cartItemId = req.getParameter("cartItemId");
                Integer delta = parseInt(req.getParameter("delta"));
                Integer quantity = parseInt(req.getParameter("quantity"));

                if (cartItemId == null || cartItemId.isBlank()) {
                    result = CartResult_24162025.fail("Dữ liệu không hợp lệ.");
                } else if (delta != null) {
                    result = cartService.changeQuantity(user.getUserId(), cartItemId, delta);
                } else if (quantity != null) {
                    result = cartService.updateQuantity(user.getUserId(), cartItemId, quantity);
                } else {
                    result = CartResult_24162025.fail("Số lượng không hợp lệ.");
                }
                break;
            }
            case "remove": {
                String cartItemId = req.getParameter("cartItemId");
                result = (cartItemId == null || cartItemId.isBlank())
                        ? CartResult_24162025.fail("Dữ liệu không hợp lệ.")
                        : cartService.removeItem(user.getUserId(), cartItemId);
                break;
            }
            case "clear":
                result = cartService.clearCart(user.getUserId());
                break;
            default:
                result = CartResult_24162025.fail("Thao tác không hợp lệ.");
        }

        refreshCartCount(req, user);

        // Post/Redirect/Get: lưu thông báo vào session, decorator sẽ hiển thị rồi xóa đi
        HttpSession session = req.getSession();
        session.setAttribute(result.isOk() ? "flashMsg" : "flashError", result.getMessage());
        resp.sendRedirect(redirectTo);
    }

    private Users_24162025 currentUser(HttpServletRequest req) {
        return (Users_24162025) req.getSession().getAttribute("currentUser");
    }

    private void refreshCartCount(HttpServletRequest req, Users_24162025 user) {
        req.getSession().setAttribute("cartCount", cartService.countItems(user.getUserId()));
    }

    private Integer parseInt(String s) {
        if (s == null) return null;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
