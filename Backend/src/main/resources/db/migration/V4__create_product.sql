CREATE TABLE product
(
    id               UUID         NOT NULL,
    vendor_id        UUID         NOT NULL,
    title            VARCHAR(255) NOT NULL,
    slug             VARCHAR(255),
    description      VARCHAR(255),
    category_id      UUID,
    tags             TEXT[],
    pickup_available BOOLEAN      NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT uc_product_slug UNIQUE (slug);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_VENDOR FOREIGN KEY (vendor_id) REFERENCES vendor (id) ON DELETE CASCADE;