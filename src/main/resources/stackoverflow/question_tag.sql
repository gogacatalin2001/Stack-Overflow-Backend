create table stackoverflow.question_tag
(
    question_tag_id bigint auto_increment
        primary key,
    question_id     bigint null,
    tag_id          bigint null,
    constraint FK44ydihbi2qk8k96175quf5q63
        foreign key (question_id) references stackoverflow.question (id),
    constraint FKnacet7y1n8llxvrbmm3xdq13j
        foreign key (tag_id) references stackoverflow.tag (id)
);
