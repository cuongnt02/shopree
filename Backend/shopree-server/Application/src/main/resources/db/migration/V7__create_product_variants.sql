CREATE TABLE product_variant
(
    id               uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id       uuid   NOT NULL REFERENCES product (id) ON DELETE CASCADE,
    sku              varchar(100) UNIQUE,
    title            varchar(255),
    price_cents      bigint NOT NULL,
    compare_at_cents bigint,
    inventory_count  int              DEFAULT 0,
    inventory_policy varchar(30)      DEFAULT 'deny_over_sell', -- or allow
    metadata         jsonb            DEFAULT '{}'::jsonb,
    created_at       timestamptz      DEFAULT now(),
    updated_at       timestamptz      DEFAULT now()
);

DROP INDEX IF EXISTS idx_products_sku;
CREATE INDEX idx_variant_sku ON product_variant(sku);