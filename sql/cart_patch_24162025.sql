-- (Tùy chọn) Chạy một lần sau database_24162025.sql.
-- Mỗi sản phẩm chỉ xuất hiện 1 lần trong một giỏ hàng, và số lượng phải > 0.
USE OnlineShop_24162025;
GO

CREATE UNIQUE INDEX UX_CartItem_Cart_Product ON CartItem(cartId, productId);
GO

ALTER TABLE CartItem ADD CONSTRAINT CK_CartItem_Quantity CHECK (quantity > 0);
GO
