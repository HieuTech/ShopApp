CREATE DATABASE IF NOT EXISTS shopapp ;
USE shopapp;

CREATE TABLE IF NOT EXISTS roles(
                                    id INT PRIMARY KEY,
                                    name VARCHAR(20) NOT NULL
);
# --Khách hàng khi muốn mua hàng => phải đăng ký tài khoản => bảng users
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_id INT,
    full_name VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(200) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);
ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles (id);




CREATE TABLE IF NOT EXISTS invalid_tokens(
    id int PRIMARY KEY AUTO_INCREMENT,
    token varchar(255) UNIQUE NOT NULL,
    token_type varchar(50) NOT NULL,
    expiration_date DATETIME,
    revoked tinyint(1) NOT NULL,
    expired tinyint(1) NOT NULL,
    user_id int
);
ALTER TABLE invalid_tokens ADD FOREIGN KEY (user_id) REFERENCES users (id);


# --Bảng danh mục sản phẩm(Category)
CREATE TABLE IF NOT EXISTS categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: đồ điện tử'
);

# --Bảng chứa sản phẩm(Product): "laptop macbook air 15 inch 2023", iphone 15 pro,...
CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) COMMENT 'Tên sản phẩm',
    price FLOAT NOT NULL CHECK (price >= 0),
    thumbnail VARCHAR(300) DEFAULT '',
    description LONGTEXT ,
    created_at DATETIME,
    updated_at DATETIME,
    category_id INT,
    active TINYINT(1)

);
ALTER TABLE products ADD FOREIGN KEY (category_id) REFERENCES categories (id);


CREATE TABLE IF NOT EXISTS product_image (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                            product_id INT NOT NULL,
    image_url VARCHAR(300)
);

ALTER TABLE product_image ADD CONSTRAINT  FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;


# --Đặt hàng - orders
CREATE TABLE IF NOT EXISTS orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id),
    full_name VARCHAR(100) DEFAULT '',
    email VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled')
        COMMENT 'Trạng thái đơn hàng',
    total_money FLOAT CHECK(total_money >= 0),
    shipping_method VARCHAR(100),
    shipping_address VARCHAR(200),
    shipping_date DATE,
    tracking_number VARCHAR(100),
    payment_method VARCHAR(100),
    active TINYINT(1)
);
ALTER TABLE orders ADD CONSTRAINT  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;


CREATE TABLE IF NOT EXISTS order_details(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products (id),
    price FLOAT CHECK(price >= 0),
    number_of_products INT CHECK(number_of_products > 0),
    total_money FLOAT CHECK(total_money >= 0),
    color VARCHAR(20) DEFAULT ''
);
ALTER TABLE order_details ADD CONSTRAINT  FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE;

