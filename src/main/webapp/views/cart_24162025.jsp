<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Giỏ hàng - Online Shop</title>
<h2>Giỏ hàng của bạn</h2>

<c:choose>
    <c:when test="${empty cartItems}">
        <div class="card">
            <p>Giỏ hàng đang trống.</p>
            <a class="btn btn-add" href="${pageContext.request.contextPath}/products">Tiếp tục mua sắm</a>
        </div>
    </c:when>
    <c:otherwise>
        <table class="data-table cart-table">
            <tr>
                <th>Sản phẩm</th>
                <th>Cửa hàng</th>
                <th>Đơn giá</th>
                <th>Số lượng</th>
                <th>Thành tiền</th>
                <th>Hành động</th>
            </tr>
            <c:forEach var="it" items="${cartItems}">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/product-detail?id=${it.productId}"><c:out value="${it.productName}"/></a>
                        <c:if test="${!it.onSale}"><div class="error-msg">Sản phẩm đã ngừng bán</div></c:if>
                        <c:if test="${it.onSale && it.overStock}"><div class="error-msg">Chỉ còn ${it.amount} sản phẩm trong kho</div></c:if>
                    </td>
                    <td><c:out value="${it.sellername}"/></td>
                    <td><fmt:formatNumber value="${it.currentPrice}" type="number" groupingUsed="true"/> đ</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/cart" class="qty-form">
                            <input type="hidden" name="action" value="update" />
                            <input type="hidden" name="cartItemId" value="${it.cartItemId}" />
                            <%-- Nút submit ẩn không có delta để khi nhấn Enter ở ô input sẽ gửi cập nhật theo số lượng nhập --%>
                            <button type="submit" style="position:absolute;left:-9999px;width:1px;height:1px;opacity:0;" tabindex="-1" aria-hidden="true"></button>
                            <button type="submit" name="delta" value="-1" class="qty-btn" ${it.quantity <= 1 ? 'disabled' : ''}>&minus;</button>
                            <input type="number" name="quantity" value="${it.quantity}" min="1" max="${it.amount}" onchange="this.form.submit()" />
                            <button type="submit" name="delta" value="1" class="qty-btn" ${it.quantity >= it.amount ? 'disabled' : ''}>+</button>
                        </form>
                        <small>Tối đa: ${it.amount}</small>
                    </td>
                    <td><fmt:formatNumber value="${it.lineTotal}" type="number" groupingUsed="true"/> đ</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/cart" style="display:inline;"
                              onsubmit="return confirm('Xóa sản phẩm này khỏi giỏ hàng?');">
                            <input type="hidden" name="action" value="remove" />
                            <input type="hidden" name="cartItemId" value="${it.cartItemId}" />
                            <button type="submit" class="btn btn-del">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <tr class="cart-total">
                <td colspan="4" style="text-align:right;"><b>Tổng cộng:</b></td>
                <td colspan="2"><b><fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true"/> đ</b></td>
            </tr>
        </table>

        <div class="action-bar" style="margin-top:14px;">
            <a class="btn btn-add" href="${pageContext.request.contextPath}/products">&laquo; Tiếp tục mua sắm</a>
            <form method="post" action="${pageContext.request.contextPath}/cart" style="display:inline;"
                  onsubmit="return confirm('Xóa toàn bộ sản phẩm trong giỏ hàng?');">
                <input type="hidden" name="action" value="clear" />
                <button type="submit" class="btn btn-del">Xóa toàn bộ giỏ hàng</button>
            </form>
        </div>
    </c:otherwise>
</c:choose>
