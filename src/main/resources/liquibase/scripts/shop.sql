-- liquibase formatted sql

-- changeset nastya:1

create table images
(
    id                    bigint not null
        primary key,
    path                  text,
    ad_id                 bigint not null
);

alter table images
    owner to student;


create table users
(
    id                    bigint not null
        primary key,
    last_name             varchar(255),
    first_name            varchar(255),
    phone                 varchar(255),
    email                 varchar(255),
    city                  varchar(255),
    image                 text,
    reg_date              varchar(255),
    username              varchar(255),
    password              varchar(255),
    role                  varchar(255)
);

alter table users
    owner to student;


create table ads
(
    id      bigint not null
        primary key,
    title                 varchar(255),
    description           text,
    price                 bigint,
    author_id             bigint not null
);

alter table ads
    owner to student;


create table comments
(
    id           bigint not null
        primary key,

    text                  text,
    author_id             bigint not null,
    created_at            timestamp,
    ad_id                 bigint not null
);

alter table comments
    owner to student;

ALTER TABLE ads
    ADD CONSTRAINT ads_author_id_fk FOREIGN KEY (author_id)
        REFERENCES users (id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE images
    ADD CONSTRAINT images_ad_id_fk FOREIGN KEY (ad_id)
        REFERENCES ads (id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE comments
    ADD CONSTRAINT comments_author_id_fk FOREIGN KEY (author_id)
        REFERENCES users (id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE comments
    ADD CONSTRAINT comments_ad_id_fk FOREIGN KEY (ad_id)
        REFERENCES ads (id)
        ON DELETE SET NULL
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

