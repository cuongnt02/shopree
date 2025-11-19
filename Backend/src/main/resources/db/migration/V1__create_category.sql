CREATE TABLE category
(
    id        UUID NOT NULL,
    name      VARCHAR(150),
    slug      VARCHAR(150),
    parent_id UUID,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

ALTER TABLE category
    ADD CONSTRAINT uc_category_slug UNIQUE (slug);

ALTER TABLE category
    ADD CONSTRAINT FK_CATEGORY_ON_PARENT FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE SET NULL;