CREATE TABLE if NOT EXISTS storages
(
    id              bigint unsigned auto_increment PRIMARY KEY,
    item_id         bigint unsigned NOT NULL,
    inventory       bigint unsigned DEFAULT 0 NOT NULL,
    locked          bigint unsigned DEFAULT 0,
    sold            bigint unsigned DEFAULT 0,
    created_at      TIMESTAMP       NOT NULL,
    updated_at      TIMESTAMP       NOT NULL,
    CONSTRAINT storage_item_id_foreign FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE
    )
    COLLATE = utf8mb4_0900_as_cs;
