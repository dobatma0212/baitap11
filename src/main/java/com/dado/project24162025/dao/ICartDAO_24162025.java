package com.dado.project24162025.dao;

import com.dado.project24162025.model.CartItem_24162025;
import java.util.List;

public interface ICartDAO_24162025 {

    /** Giỏ hàng đang dùng (status = 0) của user, null nếu chưa có. */
    String findActiveCartId(int userId);

    /** Tạo giỏ hàng mới cho user, trả về cartId (null nếu lỗi). */
    String createCart(int userId);

    List<CartItem_24162025> getItems(String cartId);
    CartItem_24162025 getItem(String cartId, String cartItemId);
    CartItem_24162025 findItemByProduct(String cartId, int productId);

    boolean insertItem(String cartId, int productId, int quantity, double unitPrice);
    boolean updateQuantity(String cartId, String cartItemId, int quantity, double unitPrice);
    boolean deleteItem(String cartId, String cartItemId);
    boolean clearItems(String cartId);

    /** Tổng số lượng sản phẩm trong giỏ. */
    int countQuantity(String cartId);
}
