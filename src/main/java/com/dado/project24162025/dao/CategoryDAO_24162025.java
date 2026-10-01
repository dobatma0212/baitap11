package com.dado.project24162025.dao;

import com.dado.project24162025.model.Category_24162025;
import com.dado.project24162025.util.DBConnection_24162025;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO_24162025 implements ICategoryDAO_24162025 {

    private Category_24162025 mapRow(ResultSet rs) throws SQLException {
        return new Category_24162025(
                rs.getInt("categoryId"),
                rs.getString("categoryName"),
                rs.getString("images"),
                rs.getInt("status")
        );
    }

    @Override
    public List<Category_24162025> getAll() {
        List<Category_24162025> list = new ArrayList<>();
        String sql = "SELECT * FROM Category ORDER BY categoryId";
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
    public List<Category_24162025> getPaged(int page, int pageSize) {
        List<Category_24162025> list = new ArrayList<>();
        String sql = "SELECT * FROM Category ORDER BY categoryId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Category";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Category_24162025 getById(int categoryId) {
        String sql = "SELECT * FROM Category WHERE categoryId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Category_24162025 category) {
        String sql = "INSERT INTO Category(categoryName, images, status) VALUES (?,?,?)";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getImages());
            ps.setInt(3, category.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Category_24162025 category) {
        String sql = "UPDATE Category SET categoryName=?, images=?, status=? WHERE categoryId=?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getImages());
            ps.setInt(3, category.getStatus());
            ps.setInt(4, category.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int categoryId) {
        String sql = "DELETE FROM Category WHERE categoryId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
