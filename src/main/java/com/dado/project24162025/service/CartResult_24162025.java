package com.dado.project24162025.service;

/** Kết quả của một thao tác giỏ hàng: thành công/thất bại kèm thông báo hiển thị cho người dùng. */
public class CartResult_24162025 {
    private final boolean ok;
    private final String message;

    private CartResult_24162025(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public static CartResult_24162025 success(String message) { return new CartResult_24162025(true, message); }
    public static CartResult_24162025 fail(String message) { return new CartResult_24162025(false, message); }

    public boolean isOk() { return ok; }
    public String getMessage() { return message; }
}
