create table stackoverflow.question_answers
(
    question_id bigint not null,
    answers_id  bigint not null,
    constraint UK_4qtn1pf4ea4ougou3ewipk9qx
        unique (answers_id),
    constraint FKlglw0r110cw97aje0b0pa4q51
        foreign key (question_id) references stackoverflow.question (id),
    constraint FKnr1xcvup15w03kboejfervq1y
        foreign key (answers_id) references stackoverflow.answer (id)
);
