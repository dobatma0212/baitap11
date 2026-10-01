<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Đơn hàng #${order.shortId} - Online Shop</title>
<h2>Đơn hàng #${order.shortId}</h2>

<div class="card">
    <p>Ngày đặt: <fmt:formatDate value="${order.buyDate}" pattern="dd/MM/yyyy HH:mm"/></p>
    <p>Trạng thái: <span class="status status-${order.status}">${order.statusText}</span></p>
    <p>Phương thức thanh toán: ${order.paymentText}</p>
    <p>Người nhận: <c:out value="${order.receiverName}"/> - <c:out value="${order.receiverPhone}"/></p>
    <p>Địa chỉ giao hàng: <c:out value="${order.shippingAddress}"/></p>
    <c:if test="${not empty order.note}"><p>Ghi chú: <c:out value="${order.note}"/></p></c:if>
</div>

<table class="data-table">
    <tr><th>Sản phẩm</th><th>Cửa hàng</th><th>Đơn giá</th><th>Số lượng</th><th>Thành tiền</th></tr>
    <c:forEach var="it" items="${order.items}">
        <tr>
            <td><a href="${pageContext.request.contextPath}/product-detail?id=${it.productId}"><c:out value="${it.productName}"/></a></td>
            <td><c:out value="${it.sellername}"/></td>
            <td><fmt:formatNumber value="${it.unitPrice}" type="number" groupingUsed="true"/> đ</td>
            <td>${it.quantity}</td>
            <td><fmt:formatNumber value="${it.orderLineTotal}" type="number" groupingUsed="true"/> đ</td>
        </tr>
    </c:forEach>
    <tr class="cart-total">
        <td colspan="4" style="text-align:right;"><b>Tổng tiền (thanh toán khi nhận hàng):</b></td>
        <td><b><fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> đ</b></td>
    </tr>
</table>

<div class="action-bar" style="margin-top:14px;">
    <a class="btn btn-add" href="${pageContext.request.contextPath}/orders">&laquo; Danh sách đơn hàng</a>
    <a class="btn btn-edit" href="${pageContext.request.contextPath}/products">Tiếp tục mua sắm</a>
</div>
