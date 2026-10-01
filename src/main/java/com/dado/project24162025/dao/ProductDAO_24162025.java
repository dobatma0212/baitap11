package com.dado.project24162025.dao;

import com.dado.project24162025.model.Product_24162025;
import com.dado.project24162025.util.DBConnection_24162025;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO_24162025 implements IProductDAO_24162025 {

    private Product_24162025 mapRow(ResultSet rs) throws SQLException {
        Product_24162025 p = new Product_24162025();
        p.setProductId(rs.getInt("productId"));
        p.setProductName(rs.getString("productName"));
        p.setProductCode(rs.getLong("productCode"));
        p.setCategoryId(rs.getInt("categoryId"));
        p.setCategoryName(rs.getString("categoryName"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setAmount(rs.getInt("amount"));
        p.setStock(rs.getInt("stock"));
        p.setImages(rs.getString("images"));
        p.setWishlist(rs.getInt("wishlist"));
        p.setStatus(rs.getInt("status"));
        p.setCreateDate(rs.getDate("createDate"));
        p.setSellerId(rs.getInt("sellerId"));
        p.setSellername(rs.getString("sellername"));
        return p;
    }

    private static final String BASE_SELECT =
            "SELECT p.*, c.categoryName, s.sellername FROM Product p "
          + "LEFT JOIN Category c ON p.categoryId = c.categoryId "
          + "LEFT JOIN Seller s ON p.sellerId = s.sellerId ";

    @Override
    public List<Product_24162025> getAllGroupedBySeller() {
        List<Product_24162025> list = new ArrayList<>();

        String sql = BASE_SELECT + "WHERE p.status = 1 ORDER BY p.sellerId, p.productId";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product_24162025 getById(int productId) {
        String sql = BASE_SELECT + "WHERE p.productId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
