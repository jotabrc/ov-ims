CREATE TABLE IF NOT EXISTS tb_order (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    placed_by VARCHAR(36) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS tb_detail (
    id BIGSERIAL PRIMARY KEY,
    product_uuid VARCHAR(36) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS tb_order_detail (
    order_id BIGINT NOT NULL,
    detail_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, detail_id),
    FOREIGN KEY (order_id) REFERENCES tb_order (id),
    FOREIGN KEY (detail_id) REFERENCES tb_detail (id)
);

CREATE INDEX idx_order_detail ON tb_order_detail (order_id, detail_id);
CREATE INDEX idx_order_uuid ON tb_order (uuid);
CREATE INDEX idx_detail_product_uuid ON tb_detail (product_uuid);
