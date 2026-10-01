package com.dado.project24162025.dao;

import com.dado.project24162025.model.Order_24162025;
import com.dado.project24162025.util.DBConnection_24162025;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO_24162025 implements IOrderDAO_24162025 {

    public static final String PAYMENT_COD = "COD";

    private static final String ORDER_SELECT =
            "SELECT c.cartId, c.userId, c.buyDate, c.status, c.receiverName, c.receiverPhone, "
          + "c.shippingAddress, c.note, c.paymentMethod, c.totalAmount, "
          + "(SELECT COALESCE(SUM(ci.quantity), 0) FROM CartItem ci WHERE ci.cartId = c.cartId) AS itemCount "
          + "FROM Cart c ";

    private Order_24162025 mapRow(ResultSet rs) throws SQLException {
        Order_24162025 o = new Order_24162025();
        o.setOrderId(rs.getString("cartId"));
        o.setUserId(rs.getInt("userId"));
        o.setBuyDate(rs.getTimestamp("buyDate"));
        o.setStatus(rs.getInt("status"));
        o.setReceiverName(rs.getString("receiverName"));
        o.setReceiverPhone(rs.getString("receiverPhone"));
        o.setShippingAddress(rs.getString("shippingAddress"));
        o.setNote(rs.getString("note"));
        o.setPaymentMethod(rs.getString("paymentMethod"));
        o.setTotalAmount(rs.getDouble("totalAmount"));
        o.setItemCount(rs.getInt("itemCount"));
        return o;
    }

    @Override
    public String placeCodOrder(String cartId, int userId, String receiverName, String receiverPhone,
                                String shippingAddress, String note) {
        Connection con = null;
        try {
            con = DBConnection_24162025.getConnection();
            con.setAutoCommit(false);

            // 1. Chuyển giỏ hàng thành đơn. Điều kiện status = 0 giúp chặn đặt hàng 2 lần (bấm đúp / 2 tab);
            //    giao dịch thứ hai sẽ chờ khóa dòng rồi không còn dòng nào khớp.
            String sqlOrder = "UPDATE Cart SET status = ?, buyDate = GETDATE(), receiverName = ?, receiverPhone = ?, "
                            + "shippingAddress = ?, note = ?, paymentMethod = ? "
                            + "WHERE cartId = ? AND userId = ? AND status = 0";
            try (PreparedStatement ps = con.prepareStatement(sqlOrder)) {
                ps.setInt(1, Order_24162025.STATUS_NEW);
                ps.setString(2, receiverName);
                ps.setString(3, receiverPhone);
                ps.setString(4, shippingAddress);
                ps.setString(5, note);
                ps.setString(6, PAYMENT_COD);
                ps.setString(7, cartId);
                ps.setInt(8, userId);
                if (ps.executeUpdate() == 0) {
                    con.rollback();
                    return "Giỏ hàng không còn hiệu lực hoặc đã được đặt hàng.";
                }
            }

            // 2. Trừ kho từng sản phẩm. Điều kiện amount >= quantity làm phép trừ an toàn khi nhiều người mua cùng lúc.
            //    Duyệt theo productId để các giao dịch khóa dòng cùng một thứ tự (tránh deadlock).
            String sqlLines = "SELECT ci.productId, ci.quantity, p.productName FROM CartItem ci "
                            + "JOIN Product p ON ci.productId = p.productId WHERE ci.cartId = ? ORDER BY ci.productId";
            List<Object[]> lines = new ArrayList<>();   // {productId, quantity, productName}
            try (PreparedStatement ps = con.prepareStatement(sqlLines)) {
                ps.setString(1, cartId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lines.add(new Object[]{rs.getInt("productId"), rs.getInt("quantity"), rs.getString("productName")});
                    }
                }
            }
            if (lines.isEmpty()) {
                con.rollback();
                return "Giỏ hàng của bạn đang trống.";
            }

            String sqlStock = "UPDATE Product SET amount = amount - ? WHERE productId = ? AND status = 1 AND amount >= ?";
            try (PreparedStatement ps = con.prepareStatement(sqlStock)) {
                for (Object[] line : lines) {
                    int productId = (Integer) line[0];
                    int qty = (Integer) line[1];
                    ps.setInt(1, qty);
                    ps.setInt(2, productId);
                    ps.setInt(3, qty);
                    if (ps.executeUpdate() == 0) {
                        con.rollback();
                        return "Sản phẩm \"" + line[2]
                                + "\" không đủ số lượng hoặc đã ngừng bán. Vui lòng kiểm tra lại giỏ hàng.";
                    }
                }
            }

            // 3. Chốt đơn giá theo giá hiện tại của sản phẩm
            String sqlPrice = "UPDATE ci SET ci.unitPrice = p.price FROM CartItem ci "
                            + "JOIN Product p ON ci.productId = p.productId WHERE ci.cartId = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlPrice)) {
                ps.setString(1, cartId);
                ps.executeUpdate();
            }

            // 4. Tính tổng tiền đơn hàng
            String sqlTotal = "UPDATE Cart SET totalAmount = "
                            + "(SELECT COALESCE(SUM(quantity * unitPrice), 0) FROM CartItem WHERE cartId = ?) "
                            + "WHERE cartId = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlTotal)) {
                ps.setString(1, cartId);
                ps.setString(2, cartId);
                ps.executeUpdate();
            }

            con.commit();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackQuietly(con);
            return "Không thể đặt hàng do lỗi hệ thống, vui lòng thử lại.";
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); } catch (SQLException ignored) {}
                try { con.close(); } catch (SQLException ignored) {}
            }
        }
    }

    private void rollbackQuietly(Connection con) {
        if (con == null) return;
        try { con.rollback(); } catch (SQLException ignored) {}
    }

    @Override
    public List<Order_24162025> getOrdersByUser(int userId) {
        return getOrdersByUser(userId, null);
    }

    @Override
    public List<Order_24162025> getOrdersByUser(int userId, Integer status) {
        List<Order_24162025> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(ORDER_SELECT).append("WHERE c.userId = ? ");
        if (status != null && status > 0) {
            sql.append("AND c.status = ? ");
        } else {
            sql.append("AND c.status > 0 ");
        }
        sql.append("ORDER BY c.buyDate DESC");

        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            ps.setInt(1, userId);
            if (status != null && status > 0) {
                ps.setInt(2, status);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<Integer, Integer> getStatusCounts(int userId) {
        Map<Integer, Integer> counts = new HashMap<>();
        String sql = "SELECT status, COUNT(*) AS cnt FROM Cart WHERE userId = ? AND status > 0 GROUP BY status";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    counts.put(rs.getInt("status"), rs.getInt("cnt"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counts;
    }

    @Override
    public Order_24162025 getOrder(int userId, String orderId) {
        String sql = ORDER_SELECT + "WHERE c.userId = ? AND c.cartId = ? AND c.status > 0";
        try (Connection con = DBConnection_24162025.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
