CREATE INDEX idx_users_email ON shopree.shopree_user (email);

CREATE INDEX idx_vendors_location ON shopree.vendor USING GIST(location);
CREATE INDEX idx_vendors_status ON shopree.vendor(status);