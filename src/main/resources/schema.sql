create table CONTESTS_CATEGORIES
(
    ID INTEGER auto_increment
        primary key,
    CATEGORY VARCHAR(20) not null
);

create table CREDENTIALS
(
    ID INTEGER auto_increment
        primary key,
    PASSWORD VARCHAR(255),
    ROLE INTEGER,
    USERNAME VARCHAR(255)
);

create table USERS
(
    ID INTEGER auto_increment
        primary key,
    FIRST_NAME VARCHAR(255),
    LAST_NAME VARCHAR(255),
    SCORE INTEGER,
    CREDENTIALS_ID INTEGER,
    constraint FK732K8FVDMF8Q8MSUL077CK3A5
        foreign key (CREDENTIALS_ID) references CREDENTIALS (ID)
);

create table CONTESTS
(
    ID INTEGER auto_increment
        primary key,
    COVER_PHOTO TEXT,
    FINISHED BOOLEAN not null,
    IS_OPEN BOOLEAN,
    TIME_LIMIT_PHASE_1 TIMESTAMP,
    TIME_LIMIT_PHASE_2 TIMESTAMP,
    TITLE VARCHAR(255),
    CATEGORY_ID INTEGER,
    constraint FK8H99WT1XMCJGD6TA8GA6GA5RT
        foreign key (CATEGORY_ID) references CONTESTS_CATEGORIES (ID)
);

create table CONTESTS_JURIES
(
    CONTEST_ID INTEGER not null,
    USER_ID INTEGER not null,
    primary key (CONTEST_ID, USER_ID),
    constraint FKK5JMO70R2JPCXFHCW3R95TD6S
        foreign key (CONTEST_ID) references CONTESTS (ID),
    constraint FKPMAWSDC53BAFSRRQDSAE0HCOC
        foreign key (USER_ID) references USERS (ID)
);

create table CONTESTS_PARTICIPANTS
(
    CONTEST_ID INTEGER not null,
    USER_ID INTEGER not null,
    primary key (CONTEST_ID, USER_ID),
    constraint FKFGN2132JAPW96X42DLYD9AAW4
        foreign key (CONTEST_ID) references CONTESTS (ID),
    constraint FKN6P6CV6XJXBS8EW3FSCMQ83OI
        foreign key (USER_ID) references USERS (ID)
);

create table PHOTOS
(
    ID INTEGER auto_increment
        primary key,
    FILE_PATH TEXT,
    STORY VARCHAR(255),
    TITLE TEXT,
    CONTEST_ID INTEGER,
    USER_ID INTEGER,
    constraint FKNM381G1KTLPSORBTPCO2LJHUV
        foreign key (USER_ID) references USERS (ID),
    constraint FKSTXAH5VK1F3WP1ELTN7CRGL1F
        foreign key (CONTEST_ID) references CONTESTS (ID)
);

create table REVIEWS
(
    ID INTEGER auto_increment
        primary key,
    COMMENT TEXT,
    EDITED BOOLEAN not null,
    SCORE INTEGER,
    PHOTO_ID INTEGER,
    JURY_ID INTEGER,
    constraint FKDO7GETLU37XFYV8XD5LB2MNSN
        foreign key (PHOTO_ID) references PHOTOS (ID),
    constraint FKOFH4NFGJR6M54LIAVS2XI2XF8
        foreign key (JURY_ID) references USERS (ID)
);


