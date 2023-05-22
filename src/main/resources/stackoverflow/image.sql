create table stackoverflow.image
(
    image_id   bigint auto_increment
        primary key,
    image_data mediumblob   not null,
    name       varchar(255) null,
    type       varchar(255) null
);
