package com.dado.project24162025.service;

/** Kết quả đặt hàng. */
public class OrderResult_24162025 {
    private final boolean ok;
    private final String message;
    private final String orderId;
    private final boolean cartProblem;   // true: lỗi do giỏ hàng (trống / hết hàng...) -> nên đưa người dùng về giỏ hàng

    private OrderResult_24162025(boolean ok, String message, String orderId, boolean cartProblem) {
        this.ok = ok;
        this.message = message;
        this.orderId = orderId;
        this.cartProblem = cartProblem;
    }

    public static OrderResult_24162025 success(String orderId) {
        return new OrderResult_24162025(true, "Đặt hàng thành công! Bạn sẽ thanh toán bằng tiền mặt khi nhận hàng.", orderId, false);
    }
    /** Lỗi nhập liệu ở form: hiển thị lại form. */
    public static OrderResult_24162025 formError(String message) {
        return new OrderResult_24162025(false, message, null, false);
    }
    /** Lỗi do giỏ hàng: chuyển về trang giỏ hàng. */
    public static OrderResult_24162025 cartError(String message) {
        return new OrderResult_24162025(false, message, null, true);
    }

    public boolean isOk() { return ok; }
    public String getMessage() { return message; }
    public String getOrderId() { return orderId; }
    public boolean isCartProblem() { return cartProblem; }
}
