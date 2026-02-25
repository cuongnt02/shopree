CREATE TABLE shopree.refresh_token (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id UUID NOT NULL REFERENCES shopree.shopree_user(id) ON DELETE CASCADE,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    device_info VARCHAR(255),
    ip_address VARCHAR(45)
);

ALTER TABLE shopree.refresh_token DROP CONSTRAINT IF EXISTS refresh_token_token_unique;
ALTER TABLE shopree.refresh_token ADD CONSTRAINT refresh_token_token_unique UNIQUE (token);

DROP INDEX IF EXISTS idx_refresh_token;
CREATE INDEX idx_refresh_token ON shopree.refresh_token(token) WHERE NOT revoked;

DROP INDEX IF EXISTS idx_refresh_token_user_id;
CREATE INDEX idx_refresh_token_user_id ON shopree.refresh_token(user_id);

DROP INDEX IF EXISTS idx_refresh_token_expires_at;
CREATE INDEX idx_refresh_token_expires_at ON shopree.refresh_token(expires_at);