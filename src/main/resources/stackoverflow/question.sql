create table stackoverflow.question
(
    title varchar(255) null,
    id    bigint       not null
        primary key,
    constraint UK_bn2drn4jy83x6xawlm2brg2ts
        unique (title),
    constraint FKd354t9nw4eyaompy2rx7gb0ie
        foreign key (id) references stackoverflow.post (id)
);
