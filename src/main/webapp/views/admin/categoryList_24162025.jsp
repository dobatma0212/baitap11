<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Quản lý Danh mục - Admin</title>
<h2>Quản lý Danh mục (Category)</h2>
<div class="action-bar">
    <a class="btn btn-add" href="${pageContext.request.contextPath}/admin/categories?action=add">+ Thêm danh mục</a>
</div>

<table class="data-table">
    <tr><th>ID</th><th>Tên danh mục</th><th>Hình ảnh</th><th>Trạng thái</th><th>Hành động</th></tr>
    <c:forEach var="c" items="${categories}">
        <tr>
            <td>${c.categoryId}</td>
            <td>${c.categoryName}</td>
            <td>${c.images}</td>
            <td>${c.status == 1 ? 'Hoạt động' : 'Ngừng hoạt động'}</td>
            <td>
                <a class="btn btn-edit" href="${pageContext.request.contextPath}/admin/categories?action=edit&id=${c.categoryId}">Sửa</a>
                <a class="btn btn-del" href="${pageContext.request.contextPath}/admin/categories?action=delete&id=${c.categoryId}"
                   onclick="return confirm('Xóa danh mục này?');">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="pagination">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <c:choose>
            <c:when test="${i == currentPage}"><span class="active">${i}</span></c:when>
            <c:otherwise><a href="${pageContext.request.contextPath}/admin/categories?page=${i}">${i}</a></c:otherwise>
        </c:choose>
    </c:forEach>
</div>
