INSERT INTO roles(name, description)
VALUES 
('ADMIN', 'Quản trị hệ thống'),
('MANAGER', 'Quản lý'),
('EMPLOYEE', 'Nhân viên')
ON CONFLICT (name) DO NOTHING;

-- USER
INSERT INTO permissions (name, description) VALUES

-- 👤 USER & AUTH
('USER_CREATE', 'Tạo người dùng'),
('USER_VIEW', 'Xem người dùng'),
('USER_UPDATE', 'Cập nhật người dùng'),
('USER_DELETE', 'Xóa người dùng'),

('ROLE_CREATE', 'Tạo vai trò'),
('ROLE_VIEW', 'Xem vai trò'),
('ROLE_UPDATE', 'Cập nhật vai trò'),
('ROLE_DELETE', 'Xóa vai trò'),

('PERMISSION_CREATE', 'Tạo quyền'),
('PERMISSION_VIEW', 'Xem quyền'),
('PERMISSION_UPDATE', 'Cập nhật quyền'),
('PERMISSION_DELETE', 'Xóa quyền'),

('PROVIDER_LINK', 'Liên kết tài khoản OAuth'),
('PROVIDER_UNLINK', 'Hủy liên kết tài khoản OAuth'),

-- 📋 LOG
('ACTIVITY_LOG_VIEW', 'Xem log hệ thống'),

-- 🏷️ MASTER DATA
('CATEGORY_CREATE', 'Tạo danh mục'),
('CATEGORY_VIEW', 'Xem danh mục'),
('CATEGORY_UPDATE', 'Cập nhật danh mục'),
('CATEGORY_DELETE', 'Xóa danh mục'),

-- 🤝 PARTNER (SUPPLIER + CUSTOMER)
('SUPPLIER_CREATE', 'Tạo nhà cung cấp'),
('SUPPLIER_VIEW', 'Xem nhà cung cấp'),
('SUPPLIER_UPDATE', 'Cập nhật nhà cung cấp'),
('SUPPLIER_DELETE', 'Xóa nhà cung cấp'),

('CUSTOMER_CREATE', 'Tạo khách hàng'),
('CUSTOMER_VIEW', 'Xem khách hàng'),
('CUSTOMER_UPDATE', 'Cập nhật khách hàng'),
('CUSTOMER_DELETE', 'Xóa khách hàng'),

-- 📦 PRODUCT
('PRODUCT_CREATE', 'Tạo sản phẩm'),
('PRODUCT_VIEW', 'Xem sản phẩm'),
('PRODUCT_UPDATE', 'Cập nhật sản phẩm'),
('PRODUCT_DELETE', 'Xóa sản phẩm'),

('PRODUCT_IMAGE_MANAGE', 'Quản lý ảnh sản phẩm'),
('PRODUCT_SUPPLIER_MANAGE', 'Quản lý NCC của sản phẩm'),

-- 📥 IMPORT
('IMPORT_ORDER_CREATE', 'Tạo phiếu nhập'),
('IMPORT_ORDER_VIEW', 'Xem phiếu nhập'),
('IMPORT_ORDER_UPDATE', 'Cập nhật phiếu nhập'),
('IMPORT_ORDER_DELETE', 'Xóa phiếu nhập'),

('IMPORT_ORDER_SUBMIT', 'Gửi duyệt phiếu nhập'),
('IMPORT_ORDER_APPROVE', 'Duyệt phiếu nhập'),
('IMPORT_ORDER_COMPLETE', 'Hoàn thành nhập kho'),
('IMPORT_ORDER_CANCEL', 'Hủy phiếu nhập'),

('BATCH_VIEW', 'Xem lô hàng'),
('BATCH_UPDATE', 'Cập nhật lô hàng'),

-- 📦 INVENTORY
('INVENTORY_VIEW', 'Xem tồn kho'),
('INVENTORY_ADJUST', 'Điều chỉnh tồn kho'),

('INVENTORY_LOG_VIEW', 'Xem lịch sử kho'),

-- 📦 INVENTORY ADJUSTMENT
('DEFECTIVE_INVENTORY_VIEW', 'Xem lịch sử hàng lỗi'),
('DEFECTIVE_INVENTORY_ADJUST', 'Điều chỉnh tồn kho lỗi'),

('DEFECTIVE_INVENTORY_LOG_VIEW', 'Xem lịch sử kho lỗi'),

-- ⚖️ KIỂM KÊ
('INVENTORY_ADJUSTMENT_CREATE', 'Tạo kiểm kê'),
('INVENTORY_ADJUSTMENT_APPROVE', 'Duyệt kiểm kê'),
('INVENTORY_ADJUSTMENT_VIEW', 'Xem kiểm kê'),

-- 🔄 SUPPLIER RETURN
('SUPPLIER_RETURN_CREATE', 'Tạo trả hàng NCC'),
('SUPPLIER_RETURN_APPROVE', 'Duyệt trả hàng NCC'),
('SUPPLIER_RETURN_COMPLETE', 'Hoàn thành trả hàng NCC'),
('SUPPLIER_RETURN_VIEW', 'Xem trả hàng NCC'),
('SUPPLIER_RETURN_CANCEL', 'Hủy trả hàng NCC'),

-- 💰 SALES
('SALES_ORDER_CREATE', 'Tạo đơn bán'),
('SALES_ORDER_VIEW', 'Xem đơn bán'),
('SALES_ORDER_UPDATE', 'Cập nhật đơn bán'),
('SALES_ORDER_DELETE', 'Xóa đơn bán'),

('SALES_ORDER_SUBMIT', 'Gửi duyệt đơn bán'),
('SALES_ORDER_APPROVE', 'Duyệt đơn bán'),
('SALES_ORDER_COMPLETE', 'Hoàn thành đơn bán'),
('SALES_ORDER_CANCEL', 'Hủy đơn bán'),

-- 🔁 CUSTOMER RETURN
('CUSTOMER_RETURN_CREATE', 'Tạo trả hàng khách'),
('CUSTOMER_RETURN_APPROVE', 'Duyệt trả hàng khách'),
('CUSTOMER_RETURN_COMPLETE', 'Hoàn thành trả hàng khách'),
('CUSTOMER_RETURN_VIEW', 'Xem trả hàng khách'),
('CUSTOMER_RETURN_CANCEL', 'Hủy trả hàng khách')
ON CONFLICT (name) DO NOTHING;

-- ==================== GÁN QUYỀN (ROLE_PERMISSIONS) ====================

-- 1. ADMIN = TOÀN QUYỀN
INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id 
FROM roles r 
CROSS JOIN permissions p 
WHERE r.name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- 2. MANAGER = Toàn quyền trừ các quyền quản trị hệ thống cao cấp
INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id 
FROM roles r
JOIN permissions p 
    ON p.name NOT IN (
        -- Các quyền cực nhạy cảm chỉ ADMIN được dùng
        'USER_CREATE', 'USER_DELETE', 'USER_UPDATE',
        'ROLE_CREATE', 'ROLE_DELETE', 'ROLE_UPDATE',
        'PERMISSION_CREATE', 'PERMISSION_DELETE', 'PERMISSION_UPDATE'
    )
WHERE r.name = 'MANAGER'
ON CONFLICT DO NOTHING;

-- 3. EMPLOYEE = Quyền hạn chế (chỉ xem + tạo một số thứ)
INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id 
FROM roles r
JOIN permissions p 
    ON p.name IN (
        'PRODUCT_VIEW', 'CATEGORY_VIEW', 'SUPPLIER_VIEW', 'CUSTOMER_VIEW',
        'IMPORT_ORDER_VIEW', 'IMPORT_ORDER_CREATE', 
        'SALES_ORDER_VIEW', 'SALES_ORDER_CREATE',
        'SUPPLIER_RETURN_VIEW', 'SUPPLIER_RETURN_CREATE',
        'CUSTOMER_RETURN_VIEW', 'CUSTOMER_RETURN_CREATE',
        'INVENTORY_VIEW', 'INVENTORY_ADJUSTMENT_VIEW', 
        'INVENTORY_ADJUSTMENT_CREATE',
        'BATCH_VIEW', 'INVENTORY_LOG_VIEW'
    )
WHERE r.name = 'EMPLOYEE'
ON CONFLICT DO NOTHING;