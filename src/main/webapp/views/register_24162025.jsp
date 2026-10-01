<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Đăng ký - Online Shop</title>
<form class="form-box" method="post" action="${pageContext.request.contextPath}/register">
    <h2>Đăng ký tài khoản</h2>
    <c:if test="${not empty error}"><p class="error-msg">${error}</p></c:if>

    <label>Họ tên</label>
    <input type="text" name="fullname" required />

    <label>Tên đăng nhập</label>
    <input type="text" name="username" required />

    <label>Email (nhận mã OTP kích hoạt)</label>
    <input type="email" name="email" required />

    <label>Số điện thoại</label>
    <input type="text" name="phone" />

    <label>Mật khẩu</label>
    <input type="password" name="password" required />

    <button type="submit">Đăng ký</button>
    <p>Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>
</form>
