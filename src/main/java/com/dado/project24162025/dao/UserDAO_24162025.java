package com.dado.project24162025.dao;

import com.dado.project24162025.model.Users_24162025;
import com.dado.project24162025.util.DBConnection_24162025;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO_24162025 implements IUserDAO_24162025 {

    private Users_24162025 mapRow(ResultSet rs) throws SQLException {
        Users_24162025 u = new Users_24162025();
        u.setUserId(rs.getInt("userId"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setFullname(rs.getString("fullname"));
        u.setPassword(rs.getString("password"));
        u.setImages(rs.getString("images"));
        u.setPhone(rs.getString("phone"));
        u.setStatus(rs.getInt("status"));
        u.setCode(rs.getString("code"));
        u.setRoleId(rs.getInt("roleId"));
        int sellerid = rs.getInt("sellerid");
        u.setSellerid(rs.wasNull() ? null : sellerid);
        return u;
    }

    @Override
    public List<Users_24162025> getAll() {
        List<Users_24162025> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY userId";
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
    public List<Users_24162025> getPaged(int page, int pageSize) {
        List<Users_24162025> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY userId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
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
        String sql = "SELECT COUNT(*) FROM Users";
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
    public Users_24162025 getById(int userId) {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Users_24162025 getByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Users_24162025 getByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Users_24162025 user) {
        String sql = "INSERT INTO Users(username, email, fullname, password, images, phone, status, code, roleId, sellerid) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getImages());
            ps.setString(6, user.getPhone());
            ps.setInt(7, user.getStatus());
            ps.setString(8, user.getCode());
            ps.setInt(9, user.getRoleId());
            if (user.getSellerid() != null) ps.setInt(10, user.getSellerid());
            else ps.setNull(10, java.sql.Types.INTEGER);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Users_24162025 user) {
        String sql = "UPDATE Users SET username=?, email=?, fullname=?, phone=?, status=?, roleId=?, sellerid=? WHERE userId=?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getPhone());
            ps.setInt(5, user.getStatus());
            ps.setInt(6, user.getRoleId());
            if (user.getSellerid() != null) ps.setInt(7, user.getSellerid());
            else ps.setNull(7, java.sql.Types.INTEGER);
            ps.setInt(8, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int userId) {
        String sql = "DELETE FROM Users WHERE userId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean activateUser(int userId) {
        String sql = "UPDATE Users SET status = 1, code = NULL WHERE userId = ?";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
