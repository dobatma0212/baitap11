package com.dado.project24162025.dao;

import com.dado.project24162025.model.CartItem_24162025;
import com.dado.project24162025.util.DBConnection_24162025;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartDAO_24162025 implements ICartDAO_24162025 {

    /** Cart.status: 0 = giỏ hàng đang dùng (chưa thanh toán). */
    public static final int STATUS_ACTIVE = 0;

    private static final String ITEM_SELECT =
            "SELECT ci.cartItemId, ci.cartId, ci.productId, ci.quantity, ci.unitPrice, "
          + "p.productName, p.productCode, p.images, p.sellerId, p.price AS currentPrice, "
          + "p.amount, p.status AS productStatus, s.sellername "
          + "FROM CartItem ci "
          + "JOIN Product p ON ci.productId = p.productId "
          + "LEFT JOIN Seller s ON p.sellerId = s.sellerId ";

    private CartItem_24162025 mapRow(ResultSet rs) throws SQLException {
        CartItem_24162025 it = new CartItem_24162025();
        it.setCartItemId(rs.getString("cartItemId"));
        it.setCartId(rs.getString("cartId"));
        it.setProductId(rs.getInt("productId"));
        it.setQuantity(rs.getInt("quantity"));
        it.setUnitPrice(rs.getDouble("unitPrice"));
        it.setProductName(rs.getString("productName"));
        it.setProductCode(rs.getLong("productCode"));
        it.setImages(rs.getString("images"));
        it.setSellerId(rs.getInt("sellerId"));
        it.setSellername(rs.getString("sellername"));
        it.setCurrentPrice(rs.getDouble("currentPrice"));
        it.setAmount(rs.getInt("amount"));
        it.setProductStatus(rs.getInt("productStatus"));
        return it;
    }

    @Override
    public String findActiveCartId(int userId) {
        String sql = "SELECT TOP 1 cartId FROM Cart WHERE userId = ? AND status = ? ORDER BY cartId";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, STATUS_ACTIVE);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String createCart(int userId) {
        String cartId = UUID.randomUUID().toString();   // 36 ký tự, vừa NVARCHAR(50)
        String sql = "INSERT INTO Cart(cartId, userId, buyDate, status) VALUES (?, ?, NULL, ?)";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.setInt(2, userId);
            ps.setInt(3, STATUS_ACTIVE);
            if (ps.executeUpdate() > 0) return cartId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CartItem_24162025> getItems(String cartId) {
        List<CartItem_24162025> list = new ArrayList<>();
        String sql = ITEM_SELECT + "WHERE ci.cartId = ? ORDER BY p.sellerId, p.productId";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public CartItem_24162025 getItem(String cartId, String cartItemId) {
        String sql = ITEM_SELECT + "WHERE ci.cartId = ? AND ci.cartItemId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.setString(2, cartItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CartItem_24162025 findItemByProduct(String cartId, int productId) {
        String sql = ITEM_SELECT + "WHERE ci.cartId = ? AND ci.productId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertItem(String cartId, int productId, int quantity, double unitPrice) {
        String sql = "INSERT INTO CartItem(cartItemId, quantity, unitPrice, productId, cartId) VALUES (?,?,?,?,?)";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setInt(2, quantity);
            ps.setDouble(3, unitPrice);
            ps.setInt(4, productId);
            ps.setString(5, cartId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateQuantity(String cartId, String cartItemId, int quantity, double unitPrice) {
        // Ràng buộc thêm cartId để user không sửa được dòng của giỏ khác
        String sql = "UPDATE CartItem SET quantity = ?, unitPrice = ? WHERE cartItemId = ? AND cartId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setDouble(2, unitPrice);
            ps.setString(3, cartItemId);
            ps.setString(4, cartId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteItem(String cartId, String cartItemId) {
        String sql = "DELETE FROM CartItem WHERE cartItemId = ? AND cartId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cartItemId);
            ps.setString(2, cartId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean clearItems(String cartId) {
        String sql = "DELETE FROM CartItem WHERE cartId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int countQuantity(String cartId) {
        String sql = "SELECT COALESCE(SUM(quantity), 0) FROM CartItem WHERE cartId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
