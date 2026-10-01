<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Quản lý User - Admin</title>
<h2>Quản lý User</h2>
<div class="action-bar">
    <a class="btn btn-add" href="${pageContext.request.contextPath}/admin/users?action=add">+ Thêm User</a>
</div>

<table class="data-table">
    <tr>
        <th>ID</th><th>Username</th><th>Email</th><th>Họ tên</th><th>SĐT</th>
        <th>Trạng thái</th><th>Role</th><th>Seller</th><th>Hành động</th>
    </tr>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.userId}</td>
            <td>${u.username}</td>
            <td>${u.email}</td>
            <td>${u.fullname}</td>
            <td>${u.phone}</td>
            <td>${u.status == 1 ? 'Đã kích hoạt' : 'Chưa kích hoạt'}</td>
            <td>
                <c:choose>
                    <c:when test="${u.roleId == 1}">Admin</c:when>
                    <c:when test="${u.roleId == 2}">Seller</c:when>
                    <c:otherwise>User</c:otherwise>
                </c:choose>
            </td>
            <td>${u.sellerid}</td>
            <td>
                <a class="btn btn-edit" href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.userId}">Sửa</a>
                <a class="btn btn-del" href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.userId}"
                   onclick="return confirm('Xóa user này?');">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="pagination">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <c:choose>
            <c:when test="${i == currentPage}"><span class="active">${i}</span></c:when>
            <c:otherwise><a href="${pageContext.request.contextPath}/admin/users?page=${i}">${i}</a></c:otherwise>
        </c:choose>
    </c:forEach>
</div>
