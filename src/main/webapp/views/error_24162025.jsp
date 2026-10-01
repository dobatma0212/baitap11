<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Không có quyền truy cập</title>
<div class="card">
    <h2>403 - Bạn không có quyền truy cập trang này</h2>
    <p><c:out value="${empty errorMessage ? 'Chức năng này chỉ dành cho Admin.' : errorMessage}"/> Vui lòng <a href="${pageContext.request.contextPath}/home">quay về trang chủ</a>.</p>
</div>
