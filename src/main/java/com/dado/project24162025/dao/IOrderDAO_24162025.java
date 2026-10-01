package com.dado.project24162025.dao;

import com.dado.project24162025.model.Order_24162025;
import java.util.List;

public interface IOrderDAO_24162025 {

    /**
     * Đặt hàng COD cho giỏ hàng đang dùng, trong MỘT transaction:
     * chuyển giỏ thành đơn (status 1), trừ kho, chốt đơn giá, tính tổng tiền.
     *
     * @return null nếu thành công, ngược lại là thông báo lỗi (transaction đã rollback).
     */
    String placeCodOrder(String cartId, int userId, String receiverName, String receiverPhone,
                         String shippingAddress, String note);

    /** Danh sách đơn hàng của user (mới nhất trước). */
    List<Order_24162025> getOrdersByUser(int userId);

    /** Một đơn hàng của user (null nếu không tồn tại hoặc không thuộc user). */
    Order_24162025 getOrder(int userId, String orderId);
}
