create database `photo_contest`;

use `photo_contest`;

create or replace table contests_categories
(
    id int auto_increment
        primary key,
    category varchar(50) not null,
    constraint categories_category_name_uindex
        unique (category)
);

create or replace table contests
(
    id int auto_increment
        primary key,
    title varchar(50) not null,
    category_id int not null,
    time_limit_phase_1 date not null,
    time_limit_phase_2 datetime not null,
    cover_photo text null,
    is_open tinyint(1) default 1 not null,
    finished tinyint(1) default 0 not null,
    constraint contests_title_uindex
        unique (title),
    constraint contests_categories_id_fk
        foreign key (category_id) references photo_contest.contests_categories (id)
);

create or replace table credentials
(
    id int auto_increment
        primary key,
    username varchar(20) not null,
    password varchar(100) not null,
    role int not null,
    constraint credentials_username_uindex
        unique (username)
);

create or replace index user_role_id_fk
    on credentials (role);

create or replace table users
(
    id int auto_increment
        primary key,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    credentials_id int not null,
    score int not null,
    constraint users_credentials_id_uindex
        unique (credentials_id),
    constraint users_credentials_id_fk
        foreign key (credentials_id) references credentials (id)
);

create or replace table contests_juries
(
    id int auto_increment
        primary key,
    contest_id int not null,
    user_id int not null,
    constraint contests_jury_contests_id_fk
        foreign key (contest_id) references contests (id),
    constraint contests_jury_users_id_fk
        foreign key (user_id) references users (id)
);

create or replace table contests_participants
(
    id int auto_increment
        primary key,
    contest_id int not null,
    user_id int not null,
    constraint contest_perticipants_contests_id_fk
        foreign key (contest_id) references contests (id),
    constraint contest_perticipants_users_id_fk
        foreign key (user_id) references users (id)
);

create or replace table photos
(
    id int auto_increment
        primary key,
    file_path text not null,
    user_id int not null,
    contest_id int not null,
    story text null,
    constraint photos_file_path_uindex
        unique (file_path) using hash,
    constraint photos_contests_id_fk
        foreign key (contest_id) references contests (id),
    constraint photos_users_id_fk
        foreign key (user_id) references users (id)
);

create or replace table reviews
(
    id int auto_increment
        primary key,
    score int not null,
    comment text null,
    jury_id int not null,
    photo_id int not null,
    edited tinyint(1) default 0 not null,
    constraint reviews_photos_id_fk
        foreign key (photo_id) references photo_contest.photos (id),
    constraint reviews_users_id_fk
        foreign key (jury_id) references photo_contest.users (id)
);

