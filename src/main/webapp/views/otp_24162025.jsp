<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Xác thực OTP - Online Shop</title>
<form class="form-box" method="post" action="${pageContext.request.contextPath}/verify-otp">
    <h2>Xác thực OTP</h2>
    <p>Một mã OTP đã được gửi tới email <b>${email}</b>. Vui lòng kiểm tra hộp thư (hoặc console server nếu chạy demo) và nhập mã bên dưới.</p>
    <c:if test="${not empty error}"><p class="error-msg">${error}</p></c:if>

    <input type="hidden" name="email" value="${email}" />
    <label>Mã OTP</label>
    <input type="text" name="otp" required />

    <button type="submit">Xác thực</button>
</form>
