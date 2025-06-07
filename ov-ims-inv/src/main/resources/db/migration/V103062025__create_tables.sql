CREATE TABLE IF NOT EXISTS tb_inventory (
    id BIGSERIAL PRIMARY KEY,
    product_uuid VARCHAR(36) NOT NULL UNIQUE,
    inventory INT NOT NULL,
    reserved_inventory INT NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    version BIGINT
);

CREATE INDEX idx_inventory_product_uuid ON tb_inventory (product_uuid);