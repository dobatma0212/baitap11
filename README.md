# Bài tập Web Bán Hàng Trực Tuyến (Online Shop) - Đề Số 06

Bài tập website thương mại điện tử đơn giản xây dựng trên nền tảng **Java Servlet / JSP (Jakarta EE)** kết hợp với cơ sở dữ liệu **Microsoft SQL Server**, sử dụng **SiteMesh 3** để quản lý giao diện Decorator.

---

## 👥 Danh Sách Tài Khoản Mặc Định

Mật khẩu mặc định cho **TẤT CẢ** các tài khoản dưới đây là: `123456` *(đã mã hóa SHA-256 trong CSDL)*.

| Vai trò (Role) | Tên đăng nhập | Mật khẩu | Họ và tên / Tên cửa hàng | Email | Ghi chú quyền hạn |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Admin** (1) | `admin` | `123456` | Quản Trị Viên | `admin@example.com` | Quản trị toàn hệ thống: CRUD người dùng (`/admin/users`), CRUD danh mục (`/admin/categories`) |
| **Seller** (2) | `seller1` | `123456` | Cửa Hàng Sách ABC | `seller1@example.com` | Người bán hàng sách ABC |
| **Seller** (2) | `seller2` | `123456` | Cửa Hàng Điện Tử XYZ | `seller2@example.com` | Người bán hàng điện tử XYZ |
| **User** (3) | `user1` | `123456` | Nguyễn Văn A | `user1@example.com` | Mua sắm, quản lý giỏ hàng (`/cart`), lịch sử đặt hàng (`/orders`) |
| **User** (3) | `user2` | `123456` | Trần Thị B | `user2@example.com` | Mua sắm, quản lý giỏ hàng (`/cart`), lịch sử đặt hàng (`/orders`) |
| **User** (3) | `user3` | `123456` | Lê Văn C | `user3@example.com` | Mua sắm, quản lý giỏ hàng (`/cart`), lịch sử đặt hàng (`/orders`) |
| **User** (3) | `user4` | `123456` | Phạm Thị D | `user4@example.com` | Mua sắm, quản lý giỏ hàng (`/cart`), lịch sử đặt hàng (`/orders`) |

---

## 🛠 Công Nghệ Sử Dụng

- **Ngôn ngữ**: Java 17+ (tương thích Java 21, 26)
- **Framework & Thư viện**:
  - Jakarta Servlet API 6.0 & JSP API 3.1
  - Jakarta JSTL 3.0 (GlassFish)
  - SiteMesh 3.2.1 (Decorator Pattern)
  - Jakarta Mail 2.1 & Angus Mail 2.0 (gửi OTP kích hoạt tài khoản)
- **Cơ sở dữ liệu**: Microsoft SQL Server (Driver: `mssql-jdbc` 12.6.2)
- **Công cụ build**: Apache Maven (`war` package)
- **Web Server**: Apache Tomcat 10+ (yêu cầu hỗ trợ Jakarta EE 10)

---

## 📁 Cấu Trúc Dự Án

```
baitap11/
├── pom.xml                                 # Cấu hình dependencies và build Maven
├── README.md                               # Hướng dẫn dự án và tài khoản mặc định
├── sql/
│   ├── database_24162025.sql               # Tạo CSDL, cấu trúc bảng và nạp dữ liệu mẫu
│   ├── cart_patch_24162025.sql             # Ràng buộc UNIQUE & CHECK cho giỏ hàng
│   ├── checkout_patch_24162025.sql         # Thêm cột thông tin giao hàng / COD cho bảng Cart
│   └── order_status_patch_24162025.sql     # Script cập nhật trạng thái & 8 đơn mẫu cho 8 trạng thái
└── src/
    └── main/
        ├── java/com/dado/project24162025/
        │   ├── controller/                 # Các Servlet điều hướng request
        │   │   ├── HomeController_...      # Trang chủ
        │   │   ├── ProductListController_..# Danh sách sản phẩm, lọc, phân trang
        │   │   ├── ProductDetailController_# Chi tiết sản phẩm
        │   │   ├── CartController_...      # Giỏ hàng (thêm, cập nhật số lượng tự động, xóa)
        │   │   ├── CheckoutController_...  # Thanh toán COD (form giao hàng, đặt hàng)
        │   │   ├── OrderController_...     # Lịch sử đặt hàng, lọc theo trạng thái, xem chi tiết đơn
        │   │   ├── LoginController_...     # Đăng nhập
        │   │   ├── LogoutController_...    # Đăng xuất
        │   │   ├── RegisterController_...  # Đăng ký tài khoản
        │   │   ├── OtpController_...       # Xác thực OTP qua Email
        │   │   ├── CategoryCrudController_ # Quản lý danh mục (Admin)
        │   │   └── UserCrudController_...  # Quản lý người dùng (Admin)
        │   ├── dao/                        # Data Access Objects (truy vấn CSDL)
        │   ├── filter/                     # Bộ lọc phân quyền (AuthFilter, UserAuthFilter)
        │   ├── model/                      # Các Entity / Model đối tượng (Order, CartItem, Product, ...)
        │   ├── service/                    # Business Logic Layer (OrderService, CartService, ...)
        │   └── util/                       # Tiện ích: DBConnection, PasswordUtil, MailUtil
        └── webapp/
            ├── assets/css/                 # File giao diện CSS (bộ màu badge 8 trạng thái, tab lọc)
            ├── views/                      # Các trang JSP giao diện người dùng
            │   ├── admin/                  # Giao diện quản trị Admin
            │   ├── cart_24162025.jsp       # Giao diện giỏ hàng
            │   ├── checkout_24162025.jsp   # Giao diện thanh toán COD
            │   ├── orderList_24162025.jsp  # Lịch sử đặt hàng với các Tab lọc theo trạng thái
            │   ├── orderDetail_24162025.jsp# Chi tiết đơn hàng
            │   └── ...
            └── WEB-INF/
                ├── decorators/             # Template layout SiteMesh cho User & Admin
                ├── sitemesh3.xml           # Cấu hình SiteMesh
                └── web.xml                 # Cấu hình servlet mappings & filter
```

---

## 🚀 Hướng Dẫn Cài Đặt & Chạy Dự Án

### 1. Cài đặt Cơ sở dữ liệu (SQL Server)
1. Mở **SQL Server Management Studio (SSMS)**.
2. Mở và thực thi file `sql/database_24162025.sql` để tạo database `OnlineShop_24162025` và nạp dữ liệu mẫu ban đầu.
3. Thực thi tiếp file `sql/cart_patch_24162025.sql` để thêm các ràng buộc toàn vẹn cho giỏ hàng.
4. Thực thi tiếp file `sql/checkout_patch_24162025.sql` để thêm các cột thông tin giao hàng / thanh toán COD vào bảng `Cart` **(bắt buộc để dùng chức năng thanh toán)**.
5. *(Khuyên dùng)* Thực thi file `sql/order_status_patch_24162025.sql` để tạo sẵn **8 đơn hàng mẫu tương ứng 8 trạng thái** cho tài khoản `user1` để kiểm tra quan sát ngay lập tức.

### 2. Cấu hình Kết nối CSDL
Nếu tài khoản hoặc mật khẩu SQL Server của bạn khác mặc định, vui lòng cập nhật trong file:
`src/main/java/com/dado/project24162025/util/DBConnection_24162025.java`
- **HOST**: `localhost`
- **PORT**: `1433`
- **DATABASE**: `OnlineShop_24162025`
- **USER**: `sa`
- **PASS**: `1234567@a$`

### 3. Build & Triển khai
1. Mở terminal tại thư mục gốc dự án và chạy lệnh build:
   ```bash
   mvn clean package
   ```
   File WAR được sinh ra tại `target/DeThi06_24162025.war`.
2. Triển khai file WAR lên **Apache Tomcat 10+** (hoặc cấu hình Tomcat Server trong Eclipse / IntelliJ / VS Code).
3. Truy cập website:
   ```
   http://localhost:8080/DeThi06_24162025/home
   ```

---

## 📌 Các Tính Năng Chính

- **Trang chủ & Sản phẩm**:
  - Xem danh sách sản phẩm theo từng người bán/cửa hàng.
  - Phân trang, xem sản phẩm theo danh mục.
  - Xem thông tin chi tiết sản phẩm và số lượng tồn kho.
- **Giỏ hàng (Chức năng dành cho User)**:
  - Thêm sản phẩm vào giỏ từ trang danh sách hoặc trang chi tiết.
  - Tăng/giảm số lượng bằng nút `+` / `-` (hệ thống tự động cập nhật ngay lập tức).
  - Tự động cập nhật số lượng khi nhập tay vào ô số lượng hoặc nhấn `Enter`.
  - Cảnh báo tồn kho khi vượt quá số lượng trong kho hoặc sản phẩm đã ngừng bán.
  - Xóa từng sản phẩm hoặc làm trống toàn bộ giỏ hàng.
- **Thanh toán COD (Chức năng dành cho User)**:
  - Từ giỏ hàng bấm **Thanh toán (COD)** (`/checkout`): nhập họ tên, số điện thoại, địa chỉ giao hàng, ghi chú (họ tên/SĐT được điền sẵn từ tài khoản).
  - Đặt hàng chạy trong **một transaction**: chuyển giỏ thành đơn (`Cart.status = 1` - Đơn hàng mới), trừ tồn kho (`Product.amount`), chốt đơn giá, tính tổng tiền. Lỗi ở bước nào thì rollback toàn bộ.
  - Chặn đặt trùng (bấm đúp / mở 2 tab) và chặn bán quá tồn kho khi nhiều người mua cùng lúc.
- **Lịch sử đặt hàng & Bộ lọc 8 trạng thái (`/orders`)**:
  - Giao diện bộ lọc dạng tab hiện đại: **Tất cả**, **Đơn hàng mới**, **Đã xác nhận**, **Chuẩn bị hàng**, **Vận chuyển**, **Giao hàng**, **Đã giao**, **Đơn hàng hủy**, **Đơn hàng hoàn**.
  - Mỗi tab hiển thị **số lượng đơn hàng thực tế** (badge count) của trạng thái tương ứng.
  - Badge trạng thái trực quan với mã màu phân biệt riêng cho từng trạng thái.
  - Xem chi tiết đơn hàng (`/orders?id=...`): danh sách sản phẩm, cửa hàng bán, số lượng, đơn giá chốt, tổng tiền, thông tin người nhận, địa chỉ giao hàng và trạng thái đơn hàng.
- **Xác thực & Bảo mật**:
  - Đăng ký tài khoản mới kèm cơ chế gửi mã OTP xác thực (nếu không cấu hình SMTP thật, OTP sẽ hiển thị ở console log của server).
  - Đăng nhập, đăng xuất, lưu session an toàn.
  - Mật khẩu mã hóa một chiều SHA-256.
  - Bộ lọc `AuthFilter` bảo vệ vùng quản trị `/admin/*` (chỉ role Admin mới truy cập được).
  - Bộ lọc `UserAuthFilter` chỉ cho phép tài khoản User truy cập `/cart`, `/checkout`, `/orders`.
- **Khu vực Quản trị (Admin)**:
  - Quản lý người dùng: Xem danh sách, thêm người dùng mới, cập nhật thông tin/vai trò, xóa người dùng.
  - Quản lý danh mục: Thêm, sửa, xóa các danh mục hàng hóa.

---

## 🔍 Hướng Dẫn Kiểm Thử Trạng Thái Đơn Hàng Trong Database

### 1. Bảng quy ước giá trị cột `Cart.status`

| Giá trị `status` | Tên trạng thái | Màu sắc hiển thị | Ý nghĩa |
| :---: | :--- | :---: | :--- |
| `0` | *Giỏ hàng đang dùng* | — | Giỏ hàng đang thêm sản phẩm, chưa bấm đặt hàng |
| **`1`** | **Đơn hàng mới** | Xanh dương nhạt | Trạng thái mặc định ngay sau khi người dùng bấm Đặt hàng COD |
| **`2`** | **Đã xác nhận** | Tím nhạt | Đơn hàng đã được xác nhận |
| **`3`** | **Chuẩn bị hàng** | Vàng hổ phách | Cửa hàng đang đóng gói sản phẩm |
| **`4`** | **Vận chuyển** | Chàm (Indigo) | Đơn hàng đã bàn giao cho đơn vị vận chuyển / đang trung chuyển |
| **`5`** | **Giao hàng** | Cam | Nhân viên giao hàng (shipper) đang đi giao cho khách |
| **`6`** | **Đã giao** | Xanh lá | Đã giao hàng thành công tới khách hàng |
| **`7`** | **Đơn hàng hủy** | Đỏ | Đơn hàng đã bị hủy |
| **`8`** | **Đơn hàng hoàn** | Xám | Đơn hàng bị trả lại kho do lỗi hoặc giao không thành công |

### 2. Cách thay đổi trạng thái trong CSDL để quan sát giao diện

Mở **SQL Server Management Studio (SSMS)** và thực hiện:

#### Bước 1: Tra cứu danh sách đơn hàng hiện có
```sql
USE OnlineShop_24162025;
GO

SELECT cartId, userId, buyDate, status, receiverName, totalAmount 
FROM Cart 
WHERE status > 0 
ORDER BY buyDate DESC;
```

#### Bước 2: Thay đổi trạng thái của đơn hàng

**Cách 1 (Nhanh nhất): Dùng 8 ký tự mã đơn hiển thị trên web (ví dụ: `#6B265115`)**
Chỉ cần lấy 8 ký tự mã đơn trên web (bỏ dấu `#`) và chạy lệnh với `LIKE`:

```sql
USE OnlineShop_24162025;
GO

-- Điền mã đơn 8 ký tự trên web của bạn (ví dụ: 6B265115)
DECLARE @shortId NVARCHAR(20) = N'6B265115';

UPDATE Cart SET status = 1 WHERE cartId LIKE @shortId + '%'; -- Đơn hàng mới
UPDATE Cart SET status = 2 WHERE cartId LIKE @shortId + '%'; -- Đã xác nhận
UPDATE Cart SET status = 3 WHERE cartId LIKE @shortId + '%'; -- Chuẩn bị hàng
UPDATE Cart SET status = 4 WHERE cartId LIKE @shortId + '%'; -- Vận chuyển
UPDATE Cart SET status = 5 WHERE cartId LIKE @shortId + '%'; -- Giao hàng
UPDATE Cart SET status = 6 WHERE cartId LIKE @shortId + '%'; -- Đã giao
UPDATE Cart SET status = 7 WHERE cartId LIKE @shortId + '%'; -- Đơn hàng hủy
UPDATE Cart SET status = 8 WHERE cartId LIKE @shortId + '%'; -- Đơn hàng hoàn
```

**Cách 2: Dùng mã `cartId` UUID 36 ký tự đầy đủ**
```sql
USE OnlineShop_24162025;
GO

DECLARE @cartId NVARCHAR(50) = N'MÃ_CART_ID_ĐẦY_ĐỦ_36_KÝ_TỰ';
UPDATE Cart SET status = 2 WHERE cartId = @cartId; -- Đổi sang Đã xác nhận
```

#### Bước 3: Quan sát trên trình duyệt
1. Đăng nhập tài khoản User (ví dụ: `user1` / `123456`).
2. Vào mục **Lịch sử đặt hàng** (`/orders`).
3. Click vào từng tab để kiểm tra bộ lọc, xem số lượng đơn cập nhật trên từng badge và quan sát trạng thái màu sắc thay đổi tương ứng.
