CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE shopree.vendor
(
    id                       uuid PRIMARY KEY      DEFAULT uuid_generate_v4(),
    owner_user_id            uuid         REFERENCES shopree.shopree_user (id) ON DELETE SET NULL,
    vendor_name              varchar(255) NOT NULL,
    slug                     varchar(255) UNIQUE,
    description              text,
    status                   varchar(30)  NOT NULL DEFAULT 'pending', -- pending | approved | rejected | suspended
    location                 geography(Point, 4326),                  -- PostGIS point (lat, lng)
    address                  jsonb,
    pickup_available         boolean               DEFAULT false,
    local_delivery_radius_km int                   DEFAULT 10,
    created_at               timestamptz           DEFAULT now(),
    updated_at               timestamptz           DEFAULT now()
);
