<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>${empty user ? 'Thêm' : 'Sửa'} User - Admin</title>
<form class="form-box" method="post" action="${pageContext.request.contextPath}/admin/users">
    <h2>${empty user ? 'Thêm User mới' : 'Cập nhật User'}</h2>
    <input type="hidden" name="action" value="save" />
    <c:if test="${not empty user}">
        <input type="hidden" name="userId" value="${user.userId}" />
    </c:if>

    <label>Username</label>
    <input type="text" name="username" value="${user.username}" required />

    <label>Email</label>
    <input type="email" name="email" value="${user.email}" required />

    <label>Họ tên</label>
    <input type="text" name="fullname" value="${user.fullname}" />

    <label>Số điện thoại</label>
    <input type="text" name="phone" value="${user.phone}" />

    <label>Trạng thái</label>
    <select name="status">
        <option value="1" ${user.status == 1 ? 'selected' : ''}>Đã kích hoạt</option>
        <option value="0" ${user.status == 0 ? 'selected' : ''}>Chưa kích hoạt</option>
    </select>

    <label>Role</label>
    <select name="roleId">
        <option value="1" ${user.roleId == 1 ? 'selected' : ''}>Admin</option>
        <option value="2" ${user.roleId == 2 ? 'selected' : ''}>Seller</option>
        <option value="3" ${(empty user or user.roleId == 3) ? 'selected' : ''}>User</option>
    </select>

    <label>Seller ID (nếu là Seller)</label>
    <input type="number" name="sellerid" value="${user.sellerid}" />

    <p><i>Lưu ý: khi thêm mới từ trang Admin, mật khẩu mặc định là <b>123456</b>.</i></p>

    <button type="submit">Lưu</button>
    <a href="${pageContext.request.contextPath}/admin/users">Hủy</a>
</form>
