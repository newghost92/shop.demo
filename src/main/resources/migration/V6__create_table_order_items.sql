CREATE TABLE if NOT EXISTS order_items
(
    id              bigint unsigned auto_increment PRIMARY KEY,
    order_id        bigint unsigned     NOT NULL,
    item_id         bigint unsigned     NOT NULL,
    item_number     INT unsigned        DEFAULT 0 NOT NULL,
    total_price     DOUBLE(17, 2)       DEFAULT 0.0 NOT NULL,
    created_at      TIMESTAMP           NOT NULL,
    updated_at      TIMESTAMP           NOT NULL,
    CONSTRAINT order_items_unique UNIQUE (order_id, item_id),
    CONSTRAINT order_items_order_id_foreign FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT order_items_item_id_foreign FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE
    )
    COLLATE = utf8mb4_0900_as_cs;
