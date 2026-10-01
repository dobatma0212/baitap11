<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Lịch sử đặt hàng - Online Shop</title>

<h2>Lịch sử đặt hàng</h2>

<!-- Thanh bộ lọc theo trạng thái đơn hàng -->
<div class="order-tabs">
    <a class="order-tab ${empty selectedStatus ? 'active' : ''}" 
       href="${pageContext.request.contextPath}/orders">
        Tất cả <span class="tab-badge">${totalOrders}</span>
    </a>
    <a class="order-tab ${selectedStatus == 1 ? 'active' : ''}" 
       href="${pageContext.request.contextPath}/orders?status=1">
        Đơn hàng mới <span class="tab-badge">${empty statusCounts[1] ? 0 : statusCounts[1]}</span>
    </a>
    <a class="order-tab ${selectedStatus == 2 ? 'active' : ''}" 
       href="${pageContext.request.contextPath}/orders?status=2">
        Đã xác nhận <span class="tab-badge">${empty statusCounts[2] ? 0 : statusCounts[2]}</span>
    </a>
    <a class="order-tab ${selectedStatus == 3 ? 'active' : ''}" 
       href="${pageContext.request.contextPath}/orders?status=3">
        Chuẩn bị hàng <span class="tab-badge">${empty statusCounts[3] ? 0 : statusCounts[3]}</span>
    </a>
    <a class="order-tab ${selectedStatus == 4 ? 'active' : ''}" 
       href="${pageContext.request.contextPath}/orders?status=4">
        Vận chuyển <span class="tab-badge">${empty statusCounts[4] ? 0 : statusCounts[4]}</span>
    </a>
    <a class="order-tab ${selectedStatus == 5 ? 'active' : ''}" 
       href="${pageContext.request.contextPath}/orders?status=5">
        Giao hàng <span class="tab-badge">${empty statusCounts[5] ? 0 : statusCounts[5]}</span>
    </a>
    <a class="order-tab ${selectedStatus == 6 ? 'active' : ''}" 
       href="${pageContext.request.contextPath}/orders?status=6">
        Đã giao <span class="tab-badge">${empty statusCounts[6] ? 0 : statusCounts[6]}</span>
    </a>
    <a class="order-tab ${selectedStatus == 7 ? 'active' : ''}" 
       href="${pageContext.request.contextPath}/orders?status=7">
        Đơn hàng hủy <span class="tab-badge">${empty statusCounts[7] ? 0 : statusCounts[7]}</span>
    </a>
    <a class="order-tab ${selectedStatus == 8 ? 'active' : ''}" 
       href="${pageContext.request.contextPath}/orders?status=8">
        Đơn hàng hoàn <span class="tab-badge">${empty statusCounts[8] ? 0 : statusCounts[8]}</span>
    </a>
</div>

<c:choose>
    <c:when test="${empty orders}">
        <div class="card" style="text-align: center; padding: 36px 20px;">
            <c:choose>
                <c:when test="${empty selectedStatus}">
                    <p style="font-size: 16px; color: #555;">Bạn chưa có đơn hàng nào.</p>
                    <a class="btn btn-add" style="margin-top: 10px;" href="${pageContext.request.contextPath}/products">Mua sắm ngay</a>
                </c:when>
                <c:otherwise>
                    <p style="font-size: 16px; color: #555;">Không có đơn hàng nào ở trạng thái này.</p>
                    <a class="btn btn-edit" style="margin-top: 10px;" href="${pageContext.request.contextPath}/orders">Xem tất cả đơn hàng</a>
                </c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:otherwise>
        <table class="data-table">
            <thead>
                <tr>
                    <th>Mã đơn</th>
                    <th>Ngày đặt</th>
                    <th>Người nhận</th>
                    <th>Số SP</th>
                    <th>Tổng tiền</th>
                    <th>Thanh toán</th>
                    <th>Trạng thái</th>
                    <th style="text-align: center;">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${orders}">
                    <tr>
                        <td><b>#${o.shortId}</b></td>
                        <td><fmt:formatDate value="${o.buyDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td><c:out value="${o.receiverName}"/><br><small style="color:#777;"><c:out value="${o.receiverPhone}"/></small></td>
                        <td>${o.itemCount}</td>
                        <td style="font-weight: 600; color: #b40000;"><fmt:formatNumber value="${o.totalAmount}" type="number" groupingUsed="true"/> đ</td>
                        <td>${o.paymentMethod}</td>
                        <td><span class="status status-${o.status}">${o.statusText}</span></td>
                        <td style="text-align: center;">
                            <a class="btn btn-edit" href="${pageContext.request.contextPath}/orders?id=${o.orderId}">Chi tiết</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
