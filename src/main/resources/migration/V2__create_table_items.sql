CREATE TABLE if NOT EXISTS items
(
    id              bigint unsigned auto_increment PRIMARY KEY,
    name            VARCHAR(250)        NOT NULL,
    description     VARCHAR(500),
    price           DOUBLE(17, 2)       NOT NULL,
    status          tinyint unsigned    DEFAULT '1' NOT NULL,
    created_at      TIMESTAMP           NOT NULL,
    updated_at      TIMESTAMP           NOT NULL,
    CONSTRAINT items_name_unique UNIQUE (name)
    )
    COLLATE = utf8mb4_0900_as_cs;
