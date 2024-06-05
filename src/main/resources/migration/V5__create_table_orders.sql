CREATE TABLE if NOT EXISTS orders
(
    id              bigint unsigned auto_increment PRIMARY KEY,
    customer_id     bigint unsigned     NOT NULL,
    total_price     DOUBLE(17, 2)       DEFAULT 0.0 NOT NULL,
    status          tinyint unsigned    DEFAULT '0' NOT NULL,
    created_at      TIMESTAMP           NOT NULL,
    updated_at      TIMESTAMP           NOT NULL
    )
    COLLATE = utf8mb4_0900_as_cs;
