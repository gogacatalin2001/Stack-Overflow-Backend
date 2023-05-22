create table stackoverflow.post_voter
(
    post_voter_id bigint auto_increment
        primary key,
    post_id       bigint null,
    user_id       bigint null,
    constraint FKddd8ahnmmv78dawdlken5vxvy
        foreign key (post_id) references stackoverflow.post (id),
    constraint FKhkp8i86cd13uhk1il3kawdejo
        foreign key (user_id) references stackoverflow.user (user_id)
);
