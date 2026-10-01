-- =====================================================================
-- BÀI TẬP 11 - MSSV: 24162025 - HỌ TÊN: BIỆN TẤN ĐÔ
-- SCRIPT CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG VÀ TẠO DỮ LIỆU MẪU QUAN SÁT
-- =====================================================================
-- Bảng quy ước trạng thái đơn hàng (Cart.status):
--   0 = Giỏ hàng đang dùng (chưa đặt hàng)
--   1 = Đơn hàng mới (trạng thái mặc định khi vừa bấm đặt hàng COD)
--   2 = Đã xác nhận
--   3 = Chuẩn bị hàng
--   4 = Vận chuyển
--   5 = Giao hàng
--   6 = Đã giao
--   7 = Đơn hàng hủy
--   8 = Đơn hàng hoàn
-- =====================================================================

USE OnlineShop_24162025;
GO

-- ---------------------------------------------------------------------
-- 1. CÂU LỆNH CẬP NHẬT TRẠNG THÁI ĐỂ QUAN SÁT THAY ĐỔI THEO TƯƠNG ỨNG
-- ---------------------------------------------------------------------
-- LƯU Ý VỀ MÃ ĐƠN HÀNG:
-- - Trên giao diện Web hiển thị mã rút gọn 8 ký tự đầu (ví dụ: #6B265115).
-- - Trong Database cột Cart.cartId lưu chuỗi UUID 36 ký tự (ví dụ: 6b265115-xxxx-xxxx-xxxx-xxxxxxxxxxxx).
--
-- >>> CÁCH 1 (KHUYÊN DÙNG - NHANH NHẤT): 
-- Chỉ cần copy 8 ký tự mã đơn trên web (bỏ dấu #) và dán vào biến @shortId dưới đây:
/*
DECLARE @shortId NVARCHAR(20) = N'6B265115'; -- Điền mã đơn 8 ký tự trên web của bạn

-- 1. Chuyển sang: Đơn hàng mới (status = 1)
UPDATE Cart SET status = 1 WHERE cartId LIKE @shortId + '%';

-- 2. Chuyển sang: Đã xác nhận (status = 2)
UPDATE Cart SET status = 2 WHERE cartId LIKE @shortId + '%';

-- 3. Chuyển sang: Chuẩn bị hàng (status = 3)
UPDATE Cart SET status = 3 WHERE cartId LIKE @shortId + '%';

-- 4. Chuyển sang: Vận chuyển (status = 4)
UPDATE Cart SET status = 4 WHERE cartId LIKE @shortId + '%';

-- 5. Chuyển sang: Giao hàng (status = 5)
UPDATE Cart SET status = 5 WHERE cartId LIKE @shortId + '%';

-- 6. Chuyển sang: Đã giao (status = 6)
UPDATE Cart SET status = 6 WHERE cartId LIKE @shortId + '%';

-- 7. Chuyển sang: Đơn hàng hủy (status = 7)
UPDATE Cart SET status = 7 WHERE cartId LIKE @shortId + '%';

-- 8. Chuyển sang: Đơn hàng hoàn (status = 8)
UPDATE Cart SET status = 8 WHERE cartId LIKE @shortId + '%';
*/

-- >>> CÁCH 2: Dùng mã cartId đầy đủ từ Database:
/*
-- Bước 2.1: Tra cứu mã cartId đầy đủ và mã ngắn:
SELECT cartId, LEFT(cartId, 8) AS maNganTrenWeb, status, receiverName, totalAmount 
FROM Cart WHERE status > 0 ORDER BY buyDate DESC;

-- Bước 2.2: Dán mã cartId đầy đủ vào biến:
DECLARE @cartId NVARCHAR(50) = N'YOUR_FULL_CART_ID';
UPDATE Cart SET status = 2 WHERE cartId = @cartId; -- Đổi sang Đã xác nhận
*/


-- ---------------------------------------------------------------------
-- 2. DỮ LIỆU MẪU KIỂM THỬ: TẠO SẴN 8 ĐƠN HÀNG TƯƠNG ỨNG 8 TRẠNG THÁI CHO user1
--    (Chạy phần này để kiểm tra ngay lập tức trên giao diện mà không cần tạo thủ công)
-- ---------------------------------------------------------------------
DECLARE @userId INT;
SELECT @userId = userId FROM Users WHERE username = 'user1';

IF @userId IS NOT NULL
BEGIN
    -- Đơn hàng 1: Đơn hàng mới (status = 1)
    IF NOT EXISTS (SELECT 1 FROM Cart WHERE cartId = 'ORDER-TEST-0001')
    BEGIN
        INSERT INTO Cart(cartId, userId, buyDate, status, receiverName, receiverPhone, shippingAddress, note, paymentMethod, totalAmount)
        VALUES ('ORDER-TEST-0001', @userId, DATEADD(MINUTE, -10, GETDATE()), 1, N'Nguyễn Văn A', '0900000003', N'Số 123 Đường Nguyễn Huệ, Q.1, TP.HCM', N'Giao giờ hành chính', 'COD', 120000);

        INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId)
        VALUES ('ITEM-TEST-0001', 1, 120000, 1, 'ORDER-TEST-0001');
    END

    -- Đơn hàng 2: Đã xác nhận (status = 2)
    IF NOT EXISTS (SELECT 1 FROM Cart WHERE cartId = 'ORDER-TEST-0002')
    BEGIN
        INSERT INTO Cart(cartId, userId, buyDate, status, receiverName, receiverPhone, shippingAddress, note, paymentMethod, totalAmount)
        VALUES ('ORDER-TEST-0002', @userId, DATEADD(HOUR, -2, GETDATE()), 2, N'Nguyễn Văn A', '0900000003', N'Số 123 Đường Nguyễn Huệ, Q.1, TP.HCM', N'', 'COD', 150000);

        INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId)
        VALUES ('ITEM-TEST-0002', 1, 150000, 2, 'ORDER-TEST-0002');
    END

    -- Đơn hàng 3: Chuẩn bị hàng (status = 3)
    IF NOT EXISTS (SELECT 1 FROM Cart WHERE cartId = 'ORDER-TEST-0003')
    BEGIN
        INSERT INTO Cart(cartId, userId, buyDate, status, receiverName, receiverPhone, shippingAddress, note, paymentMethod, totalAmount)
        VALUES ('ORDER-TEST-0003', @userId, DATEADD(HOUR, -5, GETDATE()), 3, N'Nguyễn Văn A', '0900000003', N'Số 123 Đường Nguyễn Huệ, Q.1, TP.HCM', N'Đóng gói cẩn thận giúp tôi', 'COD', 180000);

        INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId)
        VALUES ('ITEM-TEST-0003', 1, 180000, 3, 'ORDER-TEST-0003');
    END

    -- Đơn hàng 4: Vận chuyển (status = 4)
    IF NOT EXISTS (SELECT 1 FROM Cart WHERE cartId = 'ORDER-TEST-0004')
    BEGIN
        INSERT INTO Cart(cartId, userId, buyDate, status, receiverName, receiverPhone, shippingAddress, note, paymentMethod, totalAmount)
        VALUES ('ORDER-TEST-0004', @userId, DATEADD(DAY, -1, GETDATE()), 4, N'Nguyễn Văn A', '0900000003', N'Số 123 Đường Nguyễn Huệ, Q.1, TP.HCM', N'', 'COD', 29990000);

        INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId)
        VALUES ('ITEM-TEST-0004', 1, 29990000, 4, 'ORDER-TEST-0004');
    END

    -- Đơn hàng 5: Giao hàng (status = 5)
    IF NOT EXISTS (SELECT 1 FROM Cart WHERE cartId = 'ORDER-TEST-0005')
    BEGIN
        INSERT INTO Cart(cartId, userId, buyDate, status, receiverName, receiverPhone, shippingAddress, note, paymentMethod, totalAmount)
        VALUES ('ORDER-TEST-0005', @userId, DATEADD(DAY, -2, GETDATE()), 5, N'Nguyễn Văn A', '0900000003', N'Số 123 Đường Nguyễn Huệ, Q.1, TP.HCM', N'Gọi trước khi đến 15 phút', 'COD', 7490000);

        INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId)
        VALUES ('ITEM-TEST-0005', 1, 7490000, 8, 'ORDER-TEST-0005');
    END

    -- Đơn hàng 6: Đã giao (status = 6)
    IF NOT EXISTS (SELECT 1 FROM Cart WHERE cartId = 'ORDER-TEST-0006')
    BEGIN
        INSERT INTO Cart(cartId, userId, buyDate, status, receiverName, receiverPhone, shippingAddress, note, paymentMethod, totalAmount)
        VALUES ('ORDER-TEST-0006', @userId, DATEADD(DAY, -3, GETDATE()), 6, N'Nguyễn Văn A', '0900000003', N'Số 123 Đường Nguyễn Huệ, Q.1, TP.HCM', N'', 'COD', 270000);

        INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId)
        VALUES ('ITEM-TEST-0006A', 1, 120000, 1, 'ORDER-TEST-0006');
        INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId)
        VALUES ('ITEM-TEST-0006B', 1, 150000, 2, 'ORDER-TEST-0006');
    END

    -- Đơn hàng 7: Đơn hàng hủy (status = 7)
    IF NOT EXISTS (SELECT 1 FROM Cart WHERE cartId = 'ORDER-TEST-0007')
    BEGIN
        INSERT INTO Cart(cartId, userId, buyDate, status, receiverName, receiverPhone, shippingAddress, note, paymentMethod, totalAmount)
        VALUES ('ORDER-TEST-0007', @userId, DATEADD(DAY, -4, GETDATE()), 7, N'Nguyễn Văn A', '0900000003', N'Số 123 Đường Nguyễn Huệ, Q.1, TP.HCM', N'Khách đổi ý muốn mua màu khác', 'COD', 27990000);

        INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId)
        VALUES ('ITEM-TEST-0007', 1, 27990000, 5, 'ORDER-TEST-0007');
    END

    -- Đơn hàng 8: Đơn hàng hoàn (status = 8)
    IF NOT EXISTS (SELECT 1 FROM Cart WHERE cartId = 'ORDER-TEST-0008')
    BEGIN
        INSERT INTO Cart(cartId, userId, buyDate, status, receiverName, receiverPhone, shippingAddress, note, paymentMethod, totalAmount)
        VALUES ('ORDER-TEST-0008', @userId, DATEADD(DAY, -5, GETDATE()), 8, N'Nguyễn Văn A', '0900000003', N'Số 123 Đường Nguyễn Huệ, Q.1, TP.HCM', N'Hàng lỗi do vận chuyển, hoàn lại kho', 'COD', 180000);

        INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId)
        VALUES ('ITEM-TEST-0008', 1, 180000, 3, 'ORDER-TEST-0008');
    END

    PRINT N'Đã khởi tạo thành công 8 đơn hàng mẫu tương ứng 8 trạng thái cho user1!';
END
GO
