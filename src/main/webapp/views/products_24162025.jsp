<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Sản phẩm - Online Shop</title>
<h2>Danh sách sản phẩm theo cửa hàng</h2>

<c:forEach var="entry" items="${groupedProducts}">
    <div class="seller-block">
        <h3>Mã cửa hàng: ${entry.key}</h3>
        <div class="product-grid">
            <c:forEach var="p" items="${entry.value}">
                <div class="product-item card">
                    <img src="${pageContext.request.contextPath}/assets/img/${p.images}"
                         onerror="this.onerror=null;this.src='data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22200%22 height=%22140%22><rect width=%22100%25%22 height=%22100%25%22 fill=%22%23ddd%22/></svg>'" />
                    <h4><a href="${pageContext.request.contextPath}/product-detail?id=${p.productId}">${p.productName}</a></h4>
                    <p>Mã sản phẩm: ${p.productCode}</p>
                    <p>Danh mục: ${p.categoryName}</p>
                    <p>Giá: <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/> đ</p>
                    <p>Amount: ${p.amount}</p>
                    <c:if test="${sessionScope.currentUser.roleId == 3}">
                        <c:choose>
                            <c:when test="${p.amount > 0}">
                                <form method="post" action="${pageContext.request.contextPath}/cart" class="add-form">
                                    <input type="hidden" name="action" value="add" />
                                    <input type="hidden" name="productId" value="${p.productId}" />
                                    <input type="hidden" name="from" value="list" />
                                    <input type="number" name="quantity" value="1" min="1" max="${p.amount}" />
                                    <button type="submit" class="btn btn-add">Thêm vào giỏ</button>
                                </form>
                            </c:when>
                            <c:otherwise><p class="error-msg">Hết hàng</p></c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
</c:forEach>

<c:if test="${empty groupedProducts}">
    <p>Chưa có sản phẩm nào.</p>
</c:if>
