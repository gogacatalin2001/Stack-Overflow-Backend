create table stackoverflow.user
(
    user_id      bigint auto_increment
        primary key,
    banned       bit          not null,
    email        varchar(255) not null,
    password     varchar(255) not null,
    phone_number varchar(255) not null,
    role         varchar(255) not null,
    score        double       null,
    username     varchar(255) not null,
    constraint UK_4bgmpi98dylab6qdvf9xyaxu4
        unique (phone_number),
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email),
    constraint UK_sb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);
