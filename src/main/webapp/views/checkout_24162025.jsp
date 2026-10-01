<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Thanh toán - Online Shop</title>
<h2>Thanh toán đơn hàng</h2>

<div class="checkout-layout">
    <form method="post" action="${pageContext.request.contextPath}/checkout" class="form-box checkout-form">
        <h3>Thông tin giao hàng</h3>
        <c:if test="${not empty error}"><p class="error-msg"><c:out value="${error}"/></p></c:if>

        <label for="receiverName">Họ tên người nhận</label>
        <input type="text" id="receiverName" name="receiverName" maxlength="100" required
               value="<c:out value='${receiverName}'/>" />

        <label for="receiverPhone">Số điện thoại</label>
        <input type="tel" id="receiverPhone" name="receiverPhone" maxlength="20" required
               placeholder="0901234567" value="<c:out value='${receiverPhone}'/>" />

        <label for="shippingAddress">Địa chỉ giao hàng</label>
        <textarea id="shippingAddress" name="shippingAddress" rows="3" maxlength="300" required
                  placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố"><c:out value="${shippingAddress}"/></textarea>

        <label for="note">Ghi chú (không bắt buộc)</label>
        <textarea id="note" name="note" rows="2" maxlength="300"><c:out value="${note}"/></textarea>

        <label>Phương thức thanh toán</label>
        <div class="pay-method">
            <input type="radio" checked disabled /> Thanh toán khi nhận hàng (COD)
            <small>Bạn trả tiền mặt cho nhân viên giao hàng khi nhận được sản phẩm.</small>
        </div>

        <button type="submit">Đặt hàng (COD)</button>
        <a href="${pageContext.request.contextPath}/cart" class="back-link">&laquo; Quay lại giỏ hàng</a>
    </form>

    <div class="card checkout-summary">
        <h3>Đơn hàng của bạn</h3>
        <table class="data-table">
            <tr><th>Sản phẩm</th><th>SL</th><th>Thành tiền</th></tr>
            <c:forEach var="it" items="${cartItems}">
                <tr>
                    <td><c:out value="${it.productName}"/></td>
                    <td>${it.quantity}</td>
                    <td><fmt:formatNumber value="${it.lineTotal}" type="number" groupingUsed="true"/> đ</td>
                </tr>
            </c:forEach>
            <tr class="cart-total">
                <td colspan="2" style="text-align:right;"><b>Tổng thanh toán:</b></td>
                <td><b><fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true"/> đ</b></td>
            </tr>
        </table>
    </div>
</div>
