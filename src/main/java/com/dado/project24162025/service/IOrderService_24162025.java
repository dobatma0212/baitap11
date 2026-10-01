package com.dado.project24162025.service;

import com.dado.project24162025.model.CartItem_24162025;
import com.dado.project24162025.model.Order_24162025;
import java.util.List;

public interface IOrderService_24162025 {

    /** Kiểm tra giỏ hàng có thể thanh toán không. Trả về null nếu hợp lệ, ngược lại là thông báo lỗi. */
    String checkCartIssue(List<CartItem_24162025> items);

    /** Đặt hàng với phương thức thanh toán khi nhận hàng (COD). */
    OrderResult_24162025 placeCodOrder(int userId, String receiverName, String receiverPhone,
                                       String shippingAddress, String note);

    List<Order_24162025> getOrders(int userId);

    /** Lấy danh sách đơn hàng theo trạng thái (status = null hoặc <= 0 để lấy tất cả). */
    List<Order_24162025> getOrders(int userId, Integer status);

    /** Đếm số lượng đơn hàng theo từng trạng thái. */
    java.util.Map<Integer, Integer> getStatusCounts(int userId);

    /** Chi tiết đơn hàng (kèm danh sách sản phẩm), null nếu không tồn tại / không thuộc user. */
    Order_24162025 getOrderDetail(int userId, String orderId);
}
