CREATE TABLE  IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(100) NOT NULL UNIQUE,
    phone      VARCHAR(20)  NOT NULL UNIQUE
);