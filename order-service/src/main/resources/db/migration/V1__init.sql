CREATE TABLE orders
(
    id           SERIAL PRIMARY KEY,
    order_number VARCHAR(255),
    sku_code     VARCHAR(255),
    price        DECIMAL(19, 2),
    quantity     INTEGER
);
