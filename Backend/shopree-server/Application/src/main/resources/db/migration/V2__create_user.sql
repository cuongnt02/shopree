CREATE TABLE shopree."shopree_user"
(
    id            uuid PRIMARY KEY             DEFAULT uuid_generate_v4(),
    email         varchar(255) UNIQUE NOT NULL,
    phone         varchar(30),
    password_hash varchar(255)        NOT NULL,
    name          varchar(255),
    role          varchar(30)         NOT NULL DEFAULT 'buyer', -- buyer | vendor_user | admin
    metadata      jsonb                        DEFAULT '{}'::jsonb,
    verified      boolean                      DEFAULT false,
    created_at    timestamptz                  DEFAULT now(),
    updated_at    timestamptz                  DEFAULT now()
);