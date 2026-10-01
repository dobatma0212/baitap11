package com.dado.project24162025.service;

import com.dado.project24162025.dao.CartDAO_24162025;
import com.dado.project24162025.dao.ICartDAO_24162025;
import com.dado.project24162025.dao.IOrderDAO_24162025;
import com.dado.project24162025.dao.OrderDAO_24162025;
import com.dado.project24162025.model.CartItem_24162025;
import com.dado.project24162025.model.Order_24162025;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class OrderService_24162025 implements IOrderService_24162025 {

    // SĐT Việt Nam: 0xxxxxxxxx hoặc +84xxxxxxxxx (10 số / 9 số sau mã vùng)
    private static final Pattern PHONE = Pattern.compile("^(0|\\+84)[0-9]{9}$");

    private final IOrderDAO_24162025 orderDAO = new OrderDAO_24162025();
    private final ICartDAO_24162025 cartDAO = new CartDAO_24162025();

    @Override
    public String checkCartIssue(List<CartItem_24162025> items) {
        if (items == null || items.isEmpty()) return "Giỏ hàng của bạn đang trống.";
        for (CartItem_24162025 it : items) {
            if (!it.isOnSale()) {
                return "Sản phẩm \"" + it.getProductName() + "\" đã ngừng bán, vui lòng xóa khỏi giỏ hàng.";
            }
            if (it.isOverStock()) {
                return "Sản phẩm \"" + it.getProductName() + "\" chỉ còn " + it.getAmount()
                        + " trong kho, vui lòng giảm số lượng.";
            }
        }
        return null;
    }

    @Override
    public OrderResult_24162025 placeCodOrder(int userId, String receiverName, String receiverPhone,
                                              String shippingAddress, String note) {
        receiverName = trim(receiverName);
        receiverPhone = trim(receiverPhone).replaceAll("[\\s.-]", "");
        shippingAddress = trim(shippingAddress);
        note = trim(note);

        if (receiverName.length() < 2 || receiverName.length() > 100) {
            return OrderResult_24162025.formError("Họ tên người nhận phải từ 2 đến 100 ký tự.");
        }
        if (!PHONE.matcher(receiverPhone).matches()) {
            return OrderResult_24162025.formError("Số điện thoại không hợp lệ (ví dụ: 0901234567).");
        }
        if (shippingAddress.length() < 5 || shippingAddress.length() > 300) {
            return OrderResult_24162025.formError("Địa chỉ giao hàng phải từ 5 đến 300 ký tự.");
        }
        if (note.length() > 300) {
            return OrderResult_24162025.formError("Ghi chú tối đa 300 ký tự.");
        }

        String cartId = cartDAO.findActiveCartId(userId);
        List<CartItem_24162025> items = (cartId == null) ? null : cartDAO.getItems(cartId);
        String issue = checkCartIssue(items);
        if (issue != null) return OrderResult_24162025.cartError(issue);

        String error = orderDAO.placeCodOrder(cartId, userId, receiverName, receiverPhone, shippingAddress,
                note.isEmpty() ? null : note);
        return (error == null) ? OrderResult_24162025.success(cartId) : OrderResult_24162025.cartError(error);
    }

    @Override
    public List<Order_24162025> getOrders(int userId) {
        return getOrders(userId, null);
    }

    @Override
    public List<Order_24162025> getOrders(int userId, Integer status) {
        return orderDAO.getOrdersByUser(userId, status);
    }

    @Override
    public Map<Integer, Integer> getStatusCounts(int userId) {
        return orderDAO.getStatusCounts(userId);
    }

    @Override
    public Order_24162025 getOrderDetail(int userId, String orderId) {
        if (orderId == null || orderId.isBlank()) return null;
        Order_24162025 order = orderDAO.getOrder(userId, orderId);
        if (order != null) order.setItems(cartDAO.getItems(orderId));
        return order;
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
