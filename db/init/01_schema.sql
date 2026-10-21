
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,

    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,

    full_name VARCHAR(255),

    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,

    auth_version BIGINT DEFAULT 1,
    permission_version BIGINT DEFAULT 1,

    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permissions_role  FOREIGN KEY (role_id)  REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_permissions_permission FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

CREATE TABLE providers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_providers (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    provider_id BIGINT NOT NULL,
    provider_user_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_provider_user FOREIGN KEY (user_id) REFERENCES users(id)  ON DELETE CASCADE,
    CONSTRAINT fk_user_provider_provider FOREIGN KEY (provider_id) REFERENCES providers(id) ON DELETE CASCADE,
    CONSTRAINT uq_user_provider UNIQUE (user_id, provider_id)
);

CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token_hash VARCHAR(500) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_refresh_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE activity_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id BIGINT NOT NULL,
    description TEXT,
    ip_address VARCHAR(45),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_activity_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE units (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address TEXT NOT NULL UNIQUE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL ,
    phone VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address TEXT NOT NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,

    sku VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,

    category_id BIGINT NOT NULL,
    unit_id BIGINT NOT NULL,

    selling_price NUMERIC(15,2) CHECK (selling_price >= 0),

    active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    CONSTRAINT fk_product_unit FOREIGN KEY (unit_id) REFERENCES units(id)
);

CREATE TABLE product_images (
    id BIGSERIAL PRIMARY KEY,

    product_id BIGINT NOT NULL,

    image_url TEXT NOT NULL,
    alt_text VARCHAR(255),

    is_primary BOOLEAN DEFAULT FALSE,
    sort_order INT DEFAULT 0,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_image FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE product_suppliers (
    id BIGSERIAL PRIMARY KEY,

    product_id BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,

    default_price NUMERIC(15,2) CHECK (default_price >= 0),
    is_preferred BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_ps_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_ps_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE,
    CONSTRAINT unique_product_supplier UNIQUE (product_id, supplier_id)
);

CREATE TABLE import_orders (
    id BIGSERIAL PRIMARY KEY,

    supplier_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,

    status VARCHAR(30) DEFAULT 'DRAFT',
    /*
        DRAFT       : đang tạo
        PENDING     : chờ duyệt
        APPROVED    : đã duyệt
        COMPLETED   : đã nhập kho
        CANCELLED   : hủy
    */

    note TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_at TIMESTAMP,
    completed_at TIMESTAMP,
    cancelled_at TIMESTAMP,

    CONSTRAINT fk_import_supplier 
        FOREIGN KEY (supplier_id) REFERENCES suppliers(id),

    CONSTRAINT fk_import_user 
        FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE import_order_details (
    id BIGSERIAL PRIMARY KEY,

    import_order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    import_price DECIMAL(15,3) NOT NULL CHECK (import_price >= 0),
    quantity DECIMAL(15,3) NOT NULL CHECK (quantity > 0),

    note TEXT,

    CONSTRAINT fk_import_detail_order 
        FOREIGN KEY (import_order_id) 
        REFERENCES import_orders(id) ON DELETE CASCADE,

    CONSTRAINT fk_import_detail_product 
        FOREIGN KEY (product_id) REFERENCES products(id),

    CONSTRAINT uq_import_product_unit_price
        UNIQUE (import_order_id, product_id, import_price)
);


CREATE TABLE product_batches (
    id BIGSERIAL PRIMARY KEY,

    import_order_detail_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    batch_code VARCHAR(100) NOT NULL,

    import_price NUMERIC(15,2) CHECK (import_price >= 0),

    quantity DECIMAL(15,3) NOT NULL CHECK (quantity >= 0),

    remaining_quantity DECIMAL(15,3) NOT NULL CHECK (
        remaining_quantity >= 0 
        AND remaining_quantity <= quantity
    ),

    status VARCHAR(20) NOT NULL DEFAULT 'GOOD'
        CHECK (status IN ('GOOD', 'DEFECT')),
    
    manufacture_date DATE,
    expiry_date DATE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_batch_import_detail 
        FOREIGN KEY (import_order_detail_id) 
        REFERENCES import_order_details(id),

    CONSTRAINT fk_batch_product 
        FOREIGN KEY (product_id) 
        REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE inventory (
    id BIGSERIAL PRIMARY KEY,
    
    product_id BIGINT NOT NULL UNIQUE,

    quantity NUMERIC(15,3) NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE inventory_logs (
    id BIGSERIAL PRIMARY KEY,

    product_batch_id BIGINT NOT NULL,

    change_amount NUMERIC(15,3) NOT NULL,
    type VARCHAR(50) NOT NULL, 
    -- IMPORT, SALE, RETURN, ADJUST

    created_by BIGINT NOT NULL,
    note TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_inventory_log_product FOREIGN KEY (product_batch_id) REFERENCES product_batches(id) ON DELETE CASCADE
);

CREATE TABLE defective_inventory (
    id BIGSERIAL PRIMARY KEY,

    product_id BIGINT NOT NULL,
    
    quantity NUMERIC(15,3) NOT NULL CHECK (quantity >= 0),

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_defective_product 
        FOREIGN KEY (product_id) REFERENCES products(id)
);
CREATE TABLE defective_inventory_logs (
    id BIGSERIAL PRIMARY KEY,

    product_batch_id BIGINT NOT NULL,

    change_amount NUMERIC(15,3) NOT NULL,
    type VARCHAR(50) NOT NULL,
    -- IMPORT, SALE, RETURN, ADJUST
    created_by BIGINT NOT NULL,
    note TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_defective_log_product
        FOREIGN KEY (product_batch_id) REFERENCES product_batches(id)
);

CREATE TABLE inventory_adjustments (
    id BIGSERIAL PRIMARY KEY,

    created_by BIGINT NOT NULL,

    note TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_adjustment_created_by FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE inventory_adjustment_details (
    id BIGSERIAL PRIMARY KEY,

    adjustment_id BIGINT NOT NULL,
    product_batch_id BIGINT NOT NULL,

    system_quantity DECIMAL(15,3) NOT NULL,
    actual_quantity DECIMAL(15,3) NOT NULL,
    difference DECIMAL(15,3) NOT NULL,
    reason TEXT,
    
    CONSTRAINT fk_adj_detail_adjustment FOREIGN KEY (adjustment_id) REFERENCES inventory_adjustments(id) ON DELETE CASCADE,
    CONSTRAINT fk_adj_detail_batch FOREIGN KEY (product_batch_id) REFERENCES product_batches(id)
);

CREATE TABLE supplier_returns (
    id BIGSERIAL PRIMARY KEY,

    supplier_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,

    status VARCHAR(30) DEFAULT 'DRAFT',
    /*
        DRAFT       : đang tạo
        PENDING     : chờ duyệt
        APPROVED    : đã duyệt
        COMPLETED   : đã trả hàng
        CANCELLED   : hủy
    */

    note TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_at TIMESTAMP,
    completed_at TIMESTAMP,
    cancelled_at TIMESTAMP,

    CONSTRAINT fk_return_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    CONSTRAINT fk_return_user FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE supplier_return_details (
    id BIGSERIAL PRIMARY KEY,

    supplier_return_id BIGINT NOT NULL,
    product_batch_id BIGINT NOT NULL,

    quantity DECIMAL(15,3) NOT NULL CHECK (quantity > 0),

    reason TEXT,

    CONSTRAINT fk_return_detail_return FOREIGN KEY (supplier_return_id) REFERENCES supplier_returns(id) ON DELETE CASCADE,
    CONSTRAINT fk_return_detail_batch FOREIGN KEY (product_batch_id) REFERENCES product_batches(id)
);

CREATE TABLE sales_orders (
    id BIGSERIAL PRIMARY KEY,

    customer_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,

    status VARCHAR(30) DEFAULT 'DRAFT',
    /*
        DRAFT       : đang tạo
        PENDING     : chờ duyệt
        APPROVED    : đã duyệt
        COMPLETED   : đã xuất kho
        CANCELLED   : hủy
    */

    manufacture_date DATE,
    expiry_date DATE,
    
    note TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_at TIMESTAMP,
    completed_at TIMESTAMP,
    cancelled_at TIMESTAMP,

    CONSTRAINT fk_sales_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id),

    CONSTRAINT fk_sales_user
        FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE sales_order_details (
    id BIGSERIAL PRIMARY KEY,

    sales_order_id BIGINT NOT NULL,
    product_batch_id BIGINT NOT NULL,

    quantity DECIMAL(15,3) NOT NULL CHECK (quantity > 0),

    sale_price DECIMAL(15,2) CHECK (sale_price >= 0),

    note TEXT,

    CONSTRAINT fk_sales_detail_order
        FOREIGN KEY (sales_order_id)
        REFERENCES sales_orders(id) ON DELETE CASCADE,

    CONSTRAINT fk_sales_detail_batch
        FOREIGN KEY (product_batch_id)
        REFERENCES product_batches(id)
);

CREATE TABLE customer_returns (
    id BIGSERIAL PRIMARY KEY,

    sales_order_id BIGINT NOT NULL,
    created_by BIGINT NOT NULL,

    status VARCHAR(30) DEFAULT 'DRAFT',
    /*
        DRAFT       : đang tạo
        PENDING     : chờ duyệt
        APPROVED    : đã duyệt
        COMPLETED   : đã nhập lại kho
        CANCELLED   : hủy
    */

    note TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_at TIMESTAMP,
    completed_at TIMESTAMP,
    cancelled_at TIMESTAMP,

    CONSTRAINT fk_return_sales_order
        FOREIGN KEY (sales_order_id) REFERENCES sales_orders(id),

    CONSTRAINT fk_return_user
        FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE customer_return_details (
    id BIGSERIAL PRIMARY KEY,

    customer_return_id BIGINT NOT NULL,
    product_batch_id BIGINT NOT NULL,

    quantity DECIMAL(15, 3) NOT NULL CHECK (quantity > 0),

    reason TEXT,

    CONSTRAINT fk_customer_return_detail_return
        FOREIGN KEY (customer_return_id)
        REFERENCES customer_returns(id) ON DELETE CASCADE,

    CONSTRAINT fk_customer_return_detail_batch
        FOREIGN KEY (product_batch_id)
        REFERENCES product_batches(id)
);
