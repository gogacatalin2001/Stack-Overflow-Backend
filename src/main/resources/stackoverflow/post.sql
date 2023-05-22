create table stackoverflow.post
(
    id                 bigint auto_increment
        primary key,
    creation_date_time datetime(6)  null,
    text               varchar(255) null,
    vote_count         int          null,
    user_id            bigint       null,
    image_id           bigint       null,
    constraint FK72mt33dhhs48hf9gcqrq4fxte
        foreign key (user_id) references stackoverflow.user (user_id),
    constraint FKqbitxio9e3gwkkkhjhtntf16v
        foreign key (image_id) references stackoverflow.image (image_id)
);
