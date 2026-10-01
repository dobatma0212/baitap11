package com.dado.project24162025.service;

import com.dado.project24162025.model.CartItem_24162025;
import java.util.List;

public interface ICartService_24162025 {

    List<CartItem_24162025> getCartItems(int userId);
    double calcTotal(List<CartItem_24162025> items);
    int countItems(int userId);

    /** Thêm sản phẩm vào giỏ (cộng dồn nếu đã có), tổng số lượng không vượt quá số lượng còn lại. */
    CartResult_24162025 addToCart(int userId, int productId, int quantity);

    /** Đặt số lượng mới cho một dòng giỏ hàng. */
    CartResult_24162025 updateQuantity(int userId, String cartItemId, int newQuantity);

    /** Tăng / giảm số lượng (delta = +1 / -1). */
    CartResult_24162025 changeQuantity(int userId, String cartItemId, int delta);

    CartResult_24162025 removeItem(int userId, String cartItemId);
    CartResult_24162025 clearCart(int userId);
}
