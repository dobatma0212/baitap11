<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>${product.productName} - Chi tiết sản phẩm</title>
<div class="card" style="max-width:600px;margin:0 auto;">
    <img src="${pageContext.request.contextPath}/assets/img/${product.images}"
         style="width:100%;max-height:320px;object-fit:cover;border-radius:6px;"
         onerror="this.onerror=null;this.src='data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22200%22 height=%22140%22><rect width=%22100%25%22 height=%22100%25%22 fill=%22%23ddd%22/></svg>'" />
    <h2>Tên sản phẩm: ${product.productName}</h2>
    <p>Mã sản phẩm: ${product.productCode}</p>
    <p>Danh mục: ${product.categoryName}</p>
    <p>Giá: <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/> đ</p>
    <p>Amount: ${product.amount}</p>
    <p>Description: ${product.description}</p>
    <c:choose>
        <c:when test="${sessionScope.currentUser.roleId == 3}">
            <c:choose>
                <c:when test="${product.status == 1 && product.amount > 0}">
                    <form method="post" action="${pageContext.request.contextPath}/cart" class="add-form">
                        <input type="hidden" name="action" value="add" />
                        <input type="hidden" name="productId" value="${product.productId}" />
                        <input type="hidden" name="from" value="detail" />
                        <input type="number" name="quantity" value="1" min="1" max="${product.amount}" />
                        <button type="submit" class="btn btn-add">Thêm vào giỏ</button>
                    </form>
                </c:when>
                <c:otherwise><p class="error-msg">Sản phẩm hiện không thể đặt mua (hết hàng hoặc ngừng bán).</p></c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${empty sessionScope.currentUser}">
            <p><a href="${pageContext.request.contextPath}/login">Đăng nhập</a> để thêm sản phẩm vào giỏ hàng.</p>
        </c:when>
    </c:choose>
    <a href="${pageContext.request.contextPath}/products">&laquo; Quay lại danh sách sản phẩm</a>
</div>
