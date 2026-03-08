-- 初始化用户数据
INSERT INTO t_user (name, email, phone, age, status) VALUES 
('张三', 'zhangsan@example.com', '13800138000', 28, 1),
('李四', 'lisi@example.com', '13800138001', 32, 1),
('王五', 'wangwu@example.com', '13800138002', 25, 1),
('赵六', 'zhaoliu@example.com', '13800138003', 35, 1),
('钱七', 'qianqi@example.com', '13800138004', 30, 1);

-- 初始化订单数据
INSERT INTO t_order (order_no, user_id, total_amount, status) VALUES 
('ORD20240301001', 1, 299.99, 1),
('ORD20240301002', 1, 599.00, 1),
('ORD20240301003', 2, 199.50, 1),
('ORD20240301004', 3, 899.00, 2);

-- 初始化订单详情数据
INSERT INTO t_order_item (order_id, product_name, product_price, quantity, subtotal) VALUES
(1, 'iPhone 15', 299.99, 1, 299.99),
(2, 'MacBook Air', 599.00, 1, 599.00),
(3, 'iPad Air', 199.50, 1, 199.50),
(4, 'iPhone 15 Pro', 899.00, 1, 899.00);
