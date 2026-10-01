<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Đơn hàng của tôi - Online Shop</title>
<h2>Đơn hàng của tôi</h2>

<c:choose>
    <c:when test="${empty orders}">
        <div class="card">
            <p>Bạn chưa có đơn hàng nào.</p>
            <a class="btn btn-add" href="${pageContext.request.contextPath}/products">Mua sắm ngay</a>
        </div>
    </c:when>
    <c:otherwise>
        <table class="data-table">
            <tr><th>Mã đơn</th><th>Ngày đặt</th><th>Số SP</th><th>Tổng tiền</th><th>Thanh toán</th><th>Trạng thái</th><th></th></tr>
            <c:forEach var="o" items="${orders}">
                <tr>
                    <td>#${o.shortId}</td>
                    <td><fmt:formatDate value="${o.buyDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                    <td>${o.itemCount}</td>
                    <td><fmt:formatNumber value="${o.totalAmount}" type="number" groupingUsed="true"/> đ</td>
                    <td>${o.paymentMethod}</td>
                    <td><span class="status status-${o.status}">${o.statusText}</span></td>
                    <td><a class="btn btn-edit" href="${pageContext.request.contextPath}/orders?id=${o.orderId}">Chi tiết</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
