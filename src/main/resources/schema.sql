DROP TABLE IF EXISTS category;
create table category
(
    id   bigint       not null auto_increment,
    name varchar(512) not null
);

DROP TABLE IF EXISTS product;
create table product
(
    id          bigint       not null auto_increment,
    category_id bigint       not null comment 'category id',
    brand       varchar(512) not null comment 'brand 이름',
    price       int          not null comment '제품 가격'
);
create index "PRODUCT_BRAND_index"
    on PRODUCT (BRAND);

create index "PRODUCT_CATEGORY_ID_index"
    on PRODUCT (CATEGORY_ID);