package com.dado.project24162025.model;

/**
 * Một dòng trong giỏ hàng (bảng CartItem) kèm thông tin sản phẩm để hiển thị.
 */
public class CartItem_24162025 {
    private String cartItemId;
    private String cartId;
    private int productId;
    private int quantity;
    private double unitPrice;      // giá lưu trong DB tại thời điểm thêm / sửa

    // Thông tin lấy từ bảng Product / Seller (JOIN)
    private String productName;
    private long productCode;
    private String images;
    private int sellerId;
    private String sellername;
    private double currentPrice;   // giá hiện tại của sản phẩm
    private int amount;            // số lượng còn lại trong kho (giới hạn tối đa được mua)
    private int productStatus;     // 1 = đang bán

    public CartItem_24162025() {}

    /** Thành tiền = số lượng x giá hiện tại của sản phẩm. */
    public double getLineTotal() { return quantity * currentPrice; }

    /** Sản phẩm còn được bán hay không. */
    public boolean isOnSale() { return productStatus == 1; }

    /** Số lượng trong giỏ đang vượt quá số lượng còn lại (kho đã giảm sau khi thêm vào giỏ). */
    public boolean isOverStock() { return quantity > amount; }

    public String getCartItemId() { return cartItemId; }
    public void setCartItemId(String cartItemId) { this.cartItemId = cartItemId; }

    public String getCartId() { return cartId; }
    public void setCartId(String cartId) { this.cartId = cartId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public long getProductCode() { return productCode; }
    public void setProductCode(long productCode) { this.productCode = productCode; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public String getSellername() { return sellername; }
    public void setSellername(String sellername) { this.sellername = sellername; }

    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public int getProductStatus() { return productStatus; }
    public void setProductStatus(int productStatus) { this.productStatus = productStatus; }
}
