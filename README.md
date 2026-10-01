# Dự Án Web Bán Hàng Trực Tuyến (Online Shop) - Đề Số 06

Dự án website thương mại điện tử đơn giản xây dựng trên nền tảng **Java Servlet / JSP (Jakarta EE)** kết hợp với cơ sở dữ liệu **Microsoft SQL Server**, sử dụng **SiteMesh 3** để quản lý giao diện Decorator.

---

## 👥 Danh Sách Tài Khoản Mặc Định

Mật khẩu mặc định cho **TẤT CẢ** các tài khoản dưới đây là: `123456` *(đã mã hóa SHA-256 trong CSDL)*.

| Vai trò (Role) | Tên đăng nhập | Mật khẩu | Họ và tên / Tên cửa hàng | Email | Ghi chú quyền hạn |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Admin** (1) | `admin` | `123456` | Quản Trị Viên | `admin@example.com` | Quản trị toàn hệ thống: CRUD người dùng (`/admin/users`), CRUD danh mục (`/admin/categories`) |
| **Seller** (2) | `seller1` | `123456` | Cửa Hàng Sách ABC | `seller1@example.com` | Người bán hàng sách ABC |
| **Seller** (2) | `seller2` | `123456` | Cửa Hàng Điện Tử XYZ | `seller2@example.com` | Người bán hàng điện tử XYZ |
| **User** (3) | `user1` | `123456` | Nguyễn Văn A | `user1@example.com` | Mua sắm, quản lý giỏ hàng (`/cart`) |
| **User** (3) | `user2` | `123456` | Trần Thị B | `user2@example.com` | Mua sắm, quản lý giỏ hàng (`/cart`) |
| **User** (3) | `user3` | `123456` | Lê Văn C | `user3@example.com` | Mua sắm, quản lý giỏ hàng (`/cart`) |
| **User** (3) | `user4` | `123456` | Phạm Thị D | `user4@example.com` | Mua sắm, quản lý giỏ hàng (`/cart`) |

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
24162025_06_cart/
├── pom.xml                                 # Cấu hình dependencies và build Maven
├── README.md                               # Hướng dẫn dự án và tài khoản mặc định
├── sql/
│   ├── database_24162025.sql               # Tạo CSDL, cấu trúc bảng và nạp dữ liệu mẫu
│   └── cart_patch_24162025.sql             # Ràng buộc UNIQUE & CHECK cho giỏ hàng
└── src/
    └── main/
        ├── java/com/dado/project24162025/
        │   ├── controller/                 # Các Servlet điều hướng request
        │   │   ├── HomeController_...      # Trang chủ
        │   │   ├── ProductListController_..# Danh sách sản phẩm, lọc, phân trang
        │   │   ├── ProductDetailController_# Chi tiết sản phẩm
        │   │   ├── CartController_...      # Giỏ hàng (thêm, cập nhật số lượng tự động, xóa)
        │   │   ├── LoginController_...     # Đăng nhập
        │   │   ├── LogoutController_...    # Đăng xuất
        │   │   ├── RegisterController_...  # Đăng ký tài khoản
        │   │   ├── OtpController_...       # Xác thực OTP qua Email
        │   │   ├── CategoryCrudController_ # Quản lý danh mục (Admin)
        │   │   └── UserCrudController_...  # Quản lý người dùng (Admin)
        │   ├── dao/                        # Data Access Objects (truy vấn CSDL)
        │   ├── filter/                     # Bộ lọc phân quyền (AuthFilter, UserAuthFilter)
        │   ├── model/                      # Các Entity / Model đối tượng
        │   ├── service/                    # Business Logic Layer
        │   └── util/                       # Tiện ích: DBConnection, PasswordUtil, MailUtil
        └── webapp/
            ├── assets/css/                 # File giao diện CSS
            ├── views/                      # Các trang JSP giao diện người dùng
            │   ├── admin/                  # Giao diện quản trị Admin
            │   ├── cart_24162025.jsp       # Giao diện giỏ hàng
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
2. Mở và thực thi file [sql/database_24162025.sql](file:///f:/Web%20design/24162025_06_cart/sql/database_24162025.sql) để tạo database `OnlineShop_24162025` và nạp dữ liệu mẫu ban đầu.
3. Thực thi tiếp file [sql/cart_patch_24162025.sql](file:///f:/Web%20design/24162025_06_cart/sql/cart_patch_24162025.sql) để thêm các ràng buộc toàn vẹn cho giỏ hàng.

### 2. Cấu hình Kết nối CSDL
Nếu tài khoản hoặc mật khẩu SQL Server của bạn khác mặc định, vui lòng cập nhật trong file:
[src/main/java/com/dado/project24162025/util/DBConnection_24162025.java](file:///f:/Web%20design/24162025_06_cart/src/main/java/com/dado/project24162025/util/DBConnection_24162025.java)
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
- **Xác thực & Bảo mật**:
  - Đăng ký tài khoản mới kèm cơ chế gửi mã OTP xác thực (nếu không cấu hình SMTP thật, OTP sẽ hiển thị ở console log của server).
  - Đăng nhập, đăng xuất, lưu session an toàn.
  - Mật khẩu mã hóa một chiều SHA-256.
  - Bộ lọc `AuthFilter` bảo vệ vùng quản trị `/admin/*` (chỉ role Admin mới truy cập được).
  - Bộ lọc `UserAuthFilter` yêu cầu đăng nhập trước khi thao tác giỏ hàng `/cart`.
- **Khu vực Quản trị (Admin)**:
  - Quản lý người dùng: Xem danh sách, thêm người dùng mới, cập nhật thông tin/vai trò, xóa người dùng.
  - Quản lý danh mục: Thêm, sửa, xóa các danh mục hàng hóa.
