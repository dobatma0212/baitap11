CREATE DATABASE OnlineShop_24162025;
GO
USE OnlineShop_24162025;
GO

CREATE TABLE UserRoles (
    roleId INT IDENTITY(1,1) PRIMARY KEY,
    roleName NVARCHAR(50)
);
GO

CREATE TABLE Seller (
    sellerId INT IDENTITY(1,1) PRIMARY KEY,
    sellername NVARCHAR(50),
    images NVARCHAR(500),
    status INT
);
GO

CREATE TABLE Category (
    categoryId INT IDENTITY(1,1) PRIMARY KEY,
    categoryName NVARCHAR(200),
    images NVARCHAR(500),
    status INT
);
GO

CREATE TABLE Users (
    userId INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50),
    email NVARCHAR(100),
    fullname NVARCHAR(50),
    password NVARCHAR(255),
    images NVARCHAR(500),
    phone NVARCHAR(20),
    status INT,
    code NVARCHAR(50),
    roleId INT FOREIGN KEY REFERENCES UserRoles(roleId),
    sellerid INT FOREIGN KEY REFERENCES Seller(sellerId)
);
GO

CREATE TABLE Product (
    productId INT IDENTITY(1,1) PRIMARY KEY,
    productName NVARCHAR(200),
    productCode BIGINT,
    categoryId INT FOREIGN KEY REFERENCES Category(categoryId),
    description NVARCHAR(500),
    price FLOAT,
    amount INT,
    stock INT,
    images NVARCHAR(500),
    wishlist INT,
    status INT,
    createDate DATE,
    sellerId INT FOREIGN KEY REFERENCES Seller(sellerId)
);
GO

CREATE TABLE Cart (
    cartId NVARCHAR(50) PRIMARY KEY,
    userId INT FOREIGN KEY REFERENCES Users(userId),
    buyDate DATETIME,
    status INT
);
GO

CREATE TABLE CartItem (
    cartItemId NVARCHAR(50) PRIMARY KEY,
    quantity INT,
    unitPrice FLOAT,
    productId INT FOREIGN KEY REFERENCES Product(productId),
    cartId NVARCHAR(50) FOREIGN KEY REFERENCES Cart(cartId)
);
GO

INSERT INTO UserRoles(roleName) VALUES (N'Admin'), (N'Seller'), (N'User');

INSERT INTO Seller(sellername, images, status) VALUES
(N'Cua Hang Sach ABC', N'seller1.jpg', 1),
(N'Cua Hang Dien Tu XYZ', N'seller2.jpg', 1);

INSERT INTO Users(username, email, fullname, password, images, phone, status, code, roleId, sellerid) VALUES
(N'admin', N'admin@example.com', N'Quan Tri Vien', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'', N'0900000000', 1, NULL, 1, NULL),
(N'seller1', N'seller1@example.com', N'Cua Hang Sach ABC', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'seller1.jpg', N'0900000001', 1, NULL, 2, 1),
(N'seller2', N'seller2@example.com', N'Cua Hang Dien Tu XYZ', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'seller2.jpg', N'0900000002', 1, NULL, 2, 2),
(N'user1', N'user1@example.com', N'Nguyen Van A', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'', N'0900000003', 1, NULL, 3, NULL),
(N'user2', N'user2@example.com', N'Tran Thi B', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'', N'0900000004', 1, NULL, 3, NULL),
(N'user3', N'user3@example.com', N'Le Van C', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'', N'0900000005', 1, NULL, 3, NULL),
(N'user4', N'user4@example.com', N'Pham Thi D', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'', N'0900000006', 1, NULL, 3, NULL);

INSERT INTO Category(categoryName, images, status) VALUES
(N'Sach Cong Nghe', N'cat1.jpg', 1),
(N'Dien Thoai', N'cat2.jpg', 1),
(N'Laptop', N'cat3.jpg', 1),
(N'Thiet Bi Am Thanh', N'cat4.jpg', 1),
(N'Phu Kien Dien Tu', N'cat5.jpg', 1),
(N'Dong Ho Thong Minh', N'cat6.jpg', 1),
(N'Do Gia Dung', N'cat7.jpg', 1);

INSERT INTO Product(productName, productCode, categoryId, description, price, amount, stock, images, wishlist, status, createDate, sellerId) VALUES
(N'Lap Trinh Web Can Ban', 100001, 1, N'Sach hoc lap trinh web cho nguoi moi bat dau tu co ban toi nang cao.', 120000, 50, 50, N'product1.jpg', 0, 1, GETDATE(), 1),
(N'Java Nang Cao', 100002, 1, N'Sach Java chuyen sau ve Da luong, Mang va Servlet/JSP.', 150000, 30, 30, N'product2.jpg', 0, 1, GETDATE(), 1),
(N'Cau Truc Du Lieu & Giai Thuat', 100003, 1, N'Cam nang thuat toan va cau truc du lieu co ban den nang cao.', 180000, 40, 40, N'product1.jpg', 0, 1, GETDATE(), 1),
(N'iPhone 15 Pro Max', 100004, 2, N'Dien thoai iPhone 15 Pro Max 256GB chinh hang VN/A.', 29990000, 15, 15, N'product3.jpg', 0, 1, GETDATE(), 2),
(N'Samsung Galaxy S24 Ultra', 100005, 2, N'Dien thoai Samsung Galaxy S24 Ultra tich hop Galaxy AI.', 27990000, 20, 20, N'product3.jpg', 0, 1, GETDATE(), 2),
(N'Laptop Dell XPS 13', 100006, 3, N'Laptop sieu mong nhe cao cap, man hinh OLED 3K.', 32990000, 8, 8, N'product4.jpg', 0, 1, GETDATE(), 2),
(N'MacBook Pro 14 M3', 100007, 3, N'Apple MacBook Pro 14 inch chip M3 Pro manh me.', 42990000, 10, 10, N'product4.jpg', 0, 1, GETDATE(), 2),
(N'Tai nghe Sony WH-1000XM5', 100008, 4, N'Tai nghe chong on chu dong hang dau the gioi.', 7490000, 25, 25, N'product2.jpg', 0, 1, GETDATE(), 2);
GO
