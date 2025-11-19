CREATE EXTENSION IF NOT EXISTS postgis;
CREATE TABLE vendor
(
    id                       UUID         NOT NULL,
    owner_user_id            UUID,
    vendor_name              VARCHAR(255) NOT NULL,
    slug                     VARCHAR(255),
    description              VARCHAR(255),
    status                   VARCHAR(255),
    location                 GEOGRAPHY(Point, 4326),
    address                  JSONB,
    pickup_available         BOOLEAN      NOT NULL,
    local_delivery_pickup_km INTEGER      NOT NULL,
    created_at               TIMESTAMP WITHOUT TIME ZONE,
    updated_at               TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_vendor PRIMARY KEY (id)
);

ALTER TABLE vendor
    ADD CONSTRAINT uc_vendor_owner_user UNIQUE (owner_user_id);

ALTER TABLE vendor
    ADD CONSTRAINT uc_vendor_slug UNIQUE (slug);

CREATE INDEX idx_vendor_status ON vendor (status);

ALTER TABLE vendor
    ADD CONSTRAINT FK_VENDOR_ON_OWNER_USER FOREIGN KEY (owner_user_id) REFERENCES "user" (id) ON DELETE SET NULL;