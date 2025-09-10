CREATE TABLE inventory
(
    id           SERIAL PRIMARY KEY,
    sku_code     VARCHAR(255),
    quantity     INTEGER
)