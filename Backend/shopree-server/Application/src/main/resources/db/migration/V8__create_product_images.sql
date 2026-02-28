CREATE TABLE product_image
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id uuid NOT NULL REFERENCES product (id) ON DELETE CASCADE,
    url        varchar(1024) NOT NULL,
    alt_text   varchar(1024),
    created_at timestamptz NOT NULL DEFAULT now(),
    position   int              DEFAULT 0
);