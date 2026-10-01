-- Chạy một lần sau database_24162025.sql (và cart_patch_24162025.sql).
-- Bổ sung thông tin giao hàng / thanh toán COD cho bảng Cart (Cart sau khi đặt hàng chính là "đơn hàng").
--
-- Cart.status:
--   0 = giỏ hàng đang dùng (chưa đặt)
--   1 = đã đặt hàng - chờ xác nhận (COD)
--   2 = đang giao
--   3 = hoàn thành
--   4 = đã hủy
USE OnlineShop_24162025;
GO

IF COL_LENGTH('Cart', 'receiverName') IS NULL
BEGIN
    ALTER TABLE Cart ADD
        receiverName    NVARCHAR(100) NULL,
        receiverPhone   NVARCHAR(20)  NULL,
        shippingAddress NVARCHAR(300) NULL,
        note            NVARCHAR(300) NULL,
        paymentMethod   NVARCHAR(20)  NULL,   -- 'COD'
        totalAmount     FLOAT         NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_Cart_User_Status')
    CREATE INDEX IX_Cart_User_Status ON Cart(userId, status);
GO
