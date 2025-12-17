CREATE TABLE product
(
    id               uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    vendor_id        uuid         NOT NULL REFERENCES vendor (id) ON DELETE CASCADE,
    title            varchar(255) NOT NULL,
    slug             varchar(255) UNIQUE,
    description      text,
    category_id      uuid REFERENCES category (id),
    status           varchar(30)      DEFAULT 'DRAFT', -- draft | published | disabled | pending_approval
    main_image       varchar(1024),
    tags             text[],                           -- array of tags
    pickup_available boolean          DEFAULT false,
    created_at       timestamptz      DEFAULT now(),
    updated_at       timestamptz      DEFAULT now()
);

CREATE INDEX idx_products_title ON product USING gin (to_tsvector('english', title || ' ' || coalesce(description,'')));
CREATE INDEX idx_products_tags ON product  USING GIN (tags);