<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Đăng nhập - Online Shop</title>
<form class="form-box" method="post" action="${pageContext.request.contextPath}/login">
    <h2>Đăng nhập</h2>
    <c:if test="${not empty error}"><p class="error-msg">${error}</p></c:if>
    <c:if test="${not empty message}"><p class="msg-ok">${message}</p></c:if>

    <label>Tên đăng nhập</label>
    <input type="text" name="username" required />

    <label>Mật khẩu</label>
    <input type="password" name="password" required />

    <button type="submit">Đăng nhập</button>
    <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a></p>
</form>
