create table stackoverflow.tag
(
    id   bigint auto_increment
        primary key,
    text varchar(255) null,
    constraint UK_jb0t6ld04abk4k750kep9le16
        unique (text)
);
