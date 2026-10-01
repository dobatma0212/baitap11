<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Online Shop - De 06 :: <sitemesh:write property='title' /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style_24162025.css" />
    <sitemesh:write property='head' />
</head>
<body>
    <header class="site-header">
        <div class="logo"><a href="${pageContext.request.contextPath}/home">Online Shop</a></div>
        <nav>
            <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
            <a href="${pageContext.request.contextPath}/products">Sản phẩm</a>
            <c:if test="${sessionScope.currentUser.roleId == 3}">
                <a href="${pageContext.request.contextPath}/cart">Giỏ hàng (${empty sessionScope.cartCount ? 0 : sessionScope.cartCount})</a>
            </c:if>
            <c:choose>
                <c:when test="${empty sessionScope.currentUser}">
                    <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                </c:when>
                <c:otherwise>
                    <span class="hello">Xin chào, ${sessionScope.currentUser.fullname}</span>
                    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </c:otherwise>
            </c:choose>
            <c:if test="${sessionScope.currentUser.roleId == 1}">
                <a href="${pageContext.request.contextPath}/admin/users">Trang quản trị</a>
            </c:if>
        </nav>
    </header>

    <main class="site-content">
        <c:if test="${not empty sessionScope.flashMsg}">
            <div class="flash flash-ok"><c:out value="${sessionScope.flashMsg}"/></div>
            <c:remove var="flashMsg" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.flashError}">
            <div class="flash flash-error"><c:out value="${sessionScope.flashError}"/></div>
            <c:remove var="flashError" scope="session"/>
        </c:if>
        <sitemesh:write property='body' />
    </main>

    <footer class="site-footer">
        <p>Họ tên: Biện Tấn Đô &nbsp;|&nbsp; MSSV: 24162025 &nbsp;|&nbsp; Mã đề: 06</p>
    </footer>
</body>
</html>
