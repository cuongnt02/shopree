CREATE TABLE shopree_order
(
    id           uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id      uuid   NOT NULL REFERENCES shopree_user (id),
    order_number varchar(64) UNIQUE,
    status       varchar(30)      DEFAULT 'pending_payment', -- pending_payment|paid|ready_for_pickup|picked_up|canceled|refunded
    total_cents  bigint NOT NULL,
    currency     varchar(10)      DEFAULT 'VND',
    placed_at    timestamptz      DEFAULT now(),
    metadata     jsonb            DEFAULT '{}'::jsonb
);

CREATE TABLE seller_order
(
    id               uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id         uuid REFERENCES shopree_order (id) ON DELETE CASCADE,
    vendor_id        uuid   REFERENCES vendor (id) ON DELETE SET NULL,
    subtotal_cents   bigint NOT NULL,
    shipping_cents   bigint           DEFAULT 0,
    commission_cents bigint           DEFAULT 0,
    payout_cents     bigint           DEFAULT 0,
    status           varchar(30)      DEFAULT 'processing', -- processing|ready_for_pickup|picked_up|completed|canceled
    created_at       timestamptz      DEFAULT now()
);

CREATE TABLE order_item
(
    id                uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id          uuid   NOT NULL REFERENCES shopree_order (id) ON DELETE CASCADE,
    seller_order_id   uuid REFERENCES seller_order (id) ON DELETE CASCADE,
    variant_id        uuid REFERENCES product_variant (id),
    product_title     varchar(255),
    product_slug      varchar(255),
    sku               varchar(100),
    quantity          int    NOT NULL,
    unit_price_cents  bigint NOT NULL,
    total_price_cents bigint
);

CREATE TABLE payment
(
    id                uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id          uuid REFERENCES shopree_order (id) ON DELETE CASCADE,
    payment_method    varchar(50) NOT NULL,               -- card|momo|cod|wallet
    gateway_charge_id varchar(255),
    amount_cents      bigint      NOT NULL,
    currency          varchar(10)      DEFAULT 'VND',
    status            varchar(30)      DEFAULT 'pending', -- pending|succeeded|failed|refunded
    created_at        timestamptz      DEFAULT now()
);

CREATE TABLE payment_split
(
    id               uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    payment_id       uuid REFERENCES payment (id) ON DELETE CASCADE,
    seller_order_id  uuid REFERENCES seller_order (id),
    amount_cents     bigint NOT NULL,
    commission_cents bigint NOT NULL,
    escrow_held      boolean          DEFAULT true,
    payout_status    varchar(30)      DEFAULT 'pending' -- pending|scheduled|paid|failed
);

