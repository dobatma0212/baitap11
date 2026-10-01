package com.dado.project24162025.service;

import com.dado.project24162025.dao.CartDAO_24162025;
import com.dado.project24162025.dao.ICartDAO_24162025;
import com.dado.project24162025.dao.IProductDAO_24162025;
import com.dado.project24162025.dao.ProductDAO_24162025;
import com.dado.project24162025.model.CartItem_24162025;
import com.dado.project24162025.model.Product_24162025;

import java.util.ArrayList;
import java.util.List;

public class CartService_24162025 implements ICartService_24162025 {

    /** Số lượng tối thiểu của một dòng giỏ hàng. */
    public static final int MIN_QUANTITY = 1;

    private final ICartDAO_24162025 cartDAO = new CartDAO_24162025();
    private final IProductDAO_24162025 productDAO = new ProductDAO_24162025();

    /** Giới hạn tối đa được mua = số lượng còn lại của sản phẩm (cột Product.amount). */
    private int maxQuantity(int productAmount) {
        return productAmount;
    }

    @Override
    public List<CartItem_24162025> getCartItems(int userId) {
        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null) return new ArrayList<>();
        return cartDAO.getItems(cartId);
    }

    @Override
    public double calcTotal(List<CartItem_24162025> items) {
        double total = 0;
        for (CartItem_24162025 it : items) total += it.getLineTotal();
        return total;
    }

    @Override
    public int countItems(int userId) {
        String cartId = cartDAO.findActiveCartId(userId);
        return cartId == null ? 0 : cartDAO.countQuantity(cartId);
    }

    @Override
    public CartResult_24162025 addToCart(int userId, int productId, int quantity) {
        if (quantity < MIN_QUANTITY) {
            return CartResult_24162025.fail("Số lượng phải từ " + MIN_QUANTITY + " trở lên.");
        }

        Product_24162025 p = productDAO.getById(productId);
        if (p == null || p.getStatus() != 1) {
            return CartResult_24162025.fail("Sản phẩm không tồn tại hoặc đã ngừng bán.");
        }
        int max = maxQuantity(p.getAmount());
        if (max <= 0) {
            return CartResult_24162025.fail("Sản phẩm \"" + p.getProductName() + "\" đã hết hàng.");
        }

        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null) cartId = cartDAO.createCart(userId);
        if (cartId == null) return CartResult_24162025.fail("Không thể tạo giỏ hàng, vui lòng thử lại.");

        CartItem_24162025 existing = cartDAO.findItemByProduct(cartId, productId);
        int current = (existing == null) ? 0 : existing.getQuantity();
        int newQty = current + quantity;

        if (newQty > max) {
            int canAdd = Math.max(0, max - current);
            String msg = "Chỉ còn " + max + " sản phẩm \"" + p.getProductName() + "\"";
            if (current > 0) msg += " (bạn đã có " + current + " trong giỏ, chỉ có thể thêm tối đa " + canAdd + ")";
            return CartResult_24162025.fail(msg + ".");
        }

        boolean ok = (existing == null)
                ? cartDAO.insertItem(cartId, productId, newQty, p.getPrice())
                : cartDAO.updateQuantity(cartId, existing.getCartItemId(), newQty, p.getPrice());

        return ok ? CartResult_24162025.success("Đã thêm " + quantity + " \"" + p.getProductName() + "\" vào giỏ hàng.")
                  : CartResult_24162025.fail("Không thể thêm vào giỏ hàng, vui lòng thử lại.");
    }

    @Override
    public CartResult_24162025 updateQuantity(int userId, String cartItemId, int newQuantity) {
        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null) return CartResult_24162025.fail("Giỏ hàng của bạn đang trống.");

        CartItem_24162025 item = cartDAO.getItem(cartId, cartItemId);
        if (item == null) return CartResult_24162025.fail("Sản phẩm không có trong giỏ hàng.");

        if (newQuantity < MIN_QUANTITY) {
            return CartResult_24162025.fail("Số lượng tối thiểu là " + MIN_QUANTITY
                    + ". Nếu không muốn mua nữa, hãy bấm Xóa.");
        }
        if (!item.isOnSale()) {
            return CartResult_24162025.fail("Sản phẩm \"" + item.getProductName()
                    + "\" đã ngừng bán, vui lòng xóa khỏi giỏ.");
        }
        int max = maxQuantity(item.getAmount());
        if (newQuantity > max) {
            return CartResult_24162025.fail("Chỉ còn " + max + " sản phẩm \"" + item.getProductName() + "\".");
        }

        // Cập nhật luôn đơn giá theo giá hiện tại của sản phẩm
        boolean ok = cartDAO.updateQuantity(cartId, cartItemId, newQuantity, item.getCurrentPrice());
        return ok ? CartResult_24162025.success("Đã cập nhật số lượng \"" + item.getProductName() + "\" thành " + newQuantity + ".")
                  : CartResult_24162025.fail("Không thể cập nhật giỏ hàng, vui lòng thử lại.");
    }

    @Override
    public CartResult_24162025 changeQuantity(int userId, String cartItemId, int delta) {
        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null) return CartResult_24162025.fail("Giỏ hàng của bạn đang trống.");

        CartItem_24162025 item = cartDAO.getItem(cartId, cartItemId);
        if (item == null) return CartResult_24162025.fail("Sản phẩm không có trong giỏ hàng.");

        return updateQuantity(userId, cartItemId, item.getQuantity() + delta);
    }

    @Override
    public CartResult_24162025 removeItem(int userId, String cartItemId) {
        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null) return CartResult_24162025.fail("Giỏ hàng của bạn đang trống.");

        return cartDAO.deleteItem(cartId, cartItemId)
                ? CartResult_24162025.success("Đã xóa sản phẩm khỏi giỏ hàng.")
                : CartResult_24162025.fail("Sản phẩm không có trong giỏ hàng.");
    }

    @Override
    public CartResult_24162025 clearCart(int userId) {
        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null) return CartResult_24162025.success("Giỏ hàng đã trống.");

        return cartDAO.clearItems(cartId)
                ? CartResult_24162025.success("Đã xóa toàn bộ giỏ hàng.")
                : CartResult_24162025.fail("Không thể xóa giỏ hàng, vui lòng thử lại.");
    }
}
