CREATE TABLE detection
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_path               VARCHAR(255),
    camera_code              VARCHAR(255),
    customer_code                VARCHAR(255),
    recognition_status       VARCHAR(255),
    response_raw             TEXT,
    created_time             DATETIME,
    captured_time            VARCHAR(255),
    first_time_check_in      DATETIME,
    last_time_check_in       DATETIME,
    search_id                VARCHAR(255),
    score DOUBLE
);

CREATE TABLE captured_images
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    path_image               VARCHAR(255),
    detected_status       VARCHAR(255),
    captured_time            VARCHAR(255),
    customer_code                VARCHAR(255),
    camera_code              VARCHAR(255),
    search_id                VARCHAR(255),
    response_raw             TEXT,
    response_time                VARCHAR(255)
);

create index ci_index_captured_time
    on captured_images (captured_time);

create index ci_index_customer_code_IDX
    on captured_images (customer_code);

CREATE TABLE customer
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_code                VARCHAR(255) null,
    full_name                VARCHAR(255) null,
    date_of_birth                DATETIME null,
    gender                      VARCHAR(50) null,
    group_id                           int null,
    type_id                           int null,
    image_path                VARCHAR(255) null,
    status               int null,
    mobile_phone               varchar(20)  null
);

CREATE TABLE customer_type
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(255) null,
    created_time                DATETIME null,
    code                      VARCHAR(50) null,
    status               int null
);

CREATE TABLE users
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username                VARCHAR(255) null,
    password                      VARCHAR(50) null,
    created_time                DATETIME null,
    status               int null
);

CREATE TABLE customer_group
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(255) null,
    created_time                DATETIME null,
    code                      VARCHAR(50) null,
    status               int null
);

