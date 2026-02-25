CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE shopree.category
(
    id        uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name      varchar(150),
    slug      varchar(150) UNIQUE,
    parent_id uuid REFERENCES shopree.category (id) ON DELETE SET NULL
);