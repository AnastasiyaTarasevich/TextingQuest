create table quests
(
    id          int auto_increment
        primary key,
    title       varchar(255) not null,
    description longtext     not null,
    image_path  varchar(255) not null,
    prologue    longtext     not null
);

create table chapters
(
    id             int auto_increment
        primary key,
    quest_id       int          not null,
    chapter_number int          not null,
    content        longtext     not null,
    title          varchar(255) not null,
    constraint chapers_quests_id_fk
        foreign key (quest_id) references quests (id)
);

create table questions
(
    id            int auto_increment
        primary key,
    chapter_id    int      not null,
    question_text longtext not null,
    constraint questions_chapters_id_fk
        foreign key (chapter_id) references chapters (id)
);

create table answers
(
    id               int auto_increment
        primary key,
    question_id      int      not null,
    answer_text      longtext not null,
    next_question_id int      not null,
    description      longtext not null,
    constraint answers_questions_id_fk
        foreign key (question_id) references questions (id),
    constraint answers_questions_id_fk_2
        foreign key (next_question_id) references questions (id)
);

create table user
(
    id       int auto_increment
        primary key,
    nickname varchar(255) not null,
    password varchar(255) not null,
    email    varchar(255) not null,
    role     varchar(255) null
);


