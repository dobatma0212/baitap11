<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Trang Chủ - Online Shop</title>
<div class="card">
    <h2>Chào mừng đến với Online Shop</h2>
    <c:choose>
        <c:when test="${empty sessionScope.currentUser}">
            <p>Vui lòng <a href="${pageContext.request.contextPath}/login">đăng nhập</a> hoặc
               <a href="${pageContext.request.contextPath}/register">đăng ký</a> để mua sắm.</p>
        </c:when>
        <c:when test="${sessionScope.currentUser.roleId == 2}">
            <p>Đây là trang chủ dành cho <b>Seller</b>: ${sessionScope.currentUser.fullname}.</p>
            <p>Bạn có thể xem danh sách <a href="${pageContext.request.contextPath}/products">sản phẩm</a> đang được bày bán.</p>
        </c:when>
        <c:otherwise>
            <p>Đây là trang chủ dành cho <b>User</b>: ${sessionScope.currentUser.fullname}.</p>
            <p>Hãy khám phá <a href="${pageContext.request.contextPath}/products">sản phẩm</a> của chúng tôi ngay hôm nay!</p>
        </c:otherwise>
    </c:choose>
</div>
