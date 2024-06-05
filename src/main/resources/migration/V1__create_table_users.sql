CREATE TABLE if NOT EXISTS users
(
    id              bigint unsigned auto_increment PRIMARY KEY,
    username        VARCHAR(250)        NOT NULL,
    password        VARCHAR(250)        NOT NULL,
    name            VARCHAR(250)        NOT NULL,
    metadata        json                NULL,
    status          tinyint unsigned    DEFAULT '1' NOT NULL,
    created_at      TIMESTAMP           NOT NULL,
    updated_at      TIMESTAMP           NOT NULL,
    CONSTRAINT users_username_unique UNIQUE (username)
    )
    COLLATE = utf8mb4_0900_as_cs;

INSERT INTO users(`username`,`password`,`name`,`metadata`,`status`,`created_at`, `updated_at`)
VALUE ("admin", "$2a$10$hcPhUr0ykvAkgqOiJ6d91.5oYLVBGn4ZTBrbIQgxV764pZCgo3DBS", "Quản trị viên", "{}", 1, now(), now());
