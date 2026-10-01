package com.dado.project24162025.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Đơn hàng = một bản ghi Cart đã được đặt (Cart.status >= 1).
 */
public class Order_24162025 {
    public static final int STATUS_PENDING = 1;     // Chờ xác nhận
    public static final int STATUS_SHIPPING = 2;    // Đang giao
    public static final int STATUS_COMPLETED = 3;   // Hoàn thành
    public static final int STATUS_CANCELLED = 4;   // Đã hủy

    private String orderId;          // = Cart.cartId
    private int userId;
    private Date buyDate;
    private int status;
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private String note;
    private String paymentMethod;
    private double totalAmount;
    private int itemCount;           // tổng số lượng sản phẩm
    private List<CartItem_24162025> items = new ArrayList<>();

    public Order_24162025() {}

    /** Mã đơn hiển thị cho người dùng (8 ký tự đầu của cartId). */
    public String getShortId() {
        if (orderId == null) return "";
        return orderId.length() > 8 ? orderId.substring(0, 8).toUpperCase() : orderId.toUpperCase();
    }

    public String getStatusText() {
        switch (status) {
            case STATUS_PENDING:   return "Chờ xác nhận";
            case STATUS_SHIPPING:  return "Đang giao hàng";
            case STATUS_COMPLETED: return "Hoàn thành";
            case STATUS_CANCELLED: return "Đã hủy";
            default:               return "Không xác định";
        }
    }

    public String getPaymentText() {
        return "COD".equals(paymentMethod) ? "Thanh toán khi nhận hàng (COD)" : paymentMethod;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Date getBuyDate() { return buyDate; }
    public void setBuyDate(Date buyDate) { this.buyDate = buyDate; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }

    public List<CartItem_24162025> getItems() { return items; }
    public void setItems(List<CartItem_24162025> items) { this.items = items; }
}
