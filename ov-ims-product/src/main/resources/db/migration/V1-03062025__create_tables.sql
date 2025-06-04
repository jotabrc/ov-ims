CREATE TABLE IF NOT EXISTS tb_product (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    updated_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS tb_category (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    version BIGINT
);

CREATE TABLE IF NOT EXISTS tb_product_category (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES tb_product (id),
    FOREIGN KEY (category_id) REFERENCES tb_category (id)
);

CREATE INDEX idx_product_category ON tb_product_category (product_id, category_id);
CREATE INDEX idx_tb_product ON tb_product (uuid);
CREATE INDEX idx_tb_category ON tb_category (uuid);
