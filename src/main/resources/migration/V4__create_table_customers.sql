CREATE TABLE if NOT EXISTS customers
(
    id                  bigint unsigned auto_increment PRIMARY KEY,
    name                VARCHAR(250)    NOT NULL,
    address             VARCHAR(500),
    email               VARCHAR(250),
    telephone_number    VARCHAR(20),
    created_at          TIMESTAMP       NOT NULL,
    updated_at          TIMESTAMP       NOT NULL,
    CONSTRAINT customers_name_unique UNIQUE (name)
    )
    COLLATE = utf8mb4_0900_as_cs;
