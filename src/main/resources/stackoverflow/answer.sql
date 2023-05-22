create table stackoverflow.answer
(
    id          bigint not null
        primary key,
    question_id bigint null,
    constraint FK8frr4bcabmmeyyu60qt7iiblo
        foreign key (question_id) references stackoverflow.question (id),
    constraint FKnm75lqqbsmpttbbhfbm5o6rak
        foreign key (id) references stackoverflow.post (id)
);
