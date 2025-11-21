CREATE INDEX idx_users_email ON "user" (email);

CREATE INDEX idx_vendors_location ON vendor USING GIST(location);
CREATE INDEX idx_vendors_status ON vendor(status);