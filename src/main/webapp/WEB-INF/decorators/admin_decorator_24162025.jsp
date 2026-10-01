<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trang quản trị - De 06 :: <sitemesh:write property='title' /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style_24162025.css" />
    <sitemesh:write property='head' />
</head>
<body>
    <header class="site-header admin-header">
        <div class="logo"><a href="${pageContext.request.contextPath}/admin/users">Quản Trị</a></div>
        <nav>
            <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
            <a href="${pageContext.request.contextPath}/admin/users">Quản lý User</a>
            <a href="${pageContext.request.contextPath}/admin/categories">Quản lý Danh mục</a>
            <span class="hello">Xin chào, ${sessionScope.currentUser.fullname}</span>
            <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </nav>
    </header>

    <main class="site-content">
        <sitemesh:write property='body' />
    </main>

    <footer class="site-footer">
        <p>Họ tên: Biện Tấn Đô &nbsp;|&nbsp; MSSV: 24162025 &nbsp;|&nbsp; Mã đề: 06</p>
    </footer>
</body>
</html>
