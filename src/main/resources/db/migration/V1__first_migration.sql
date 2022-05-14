-- DROP TABLE users;

CREATE SCHEMA IF NOT EXISTS test_schema;

CREATE TABLE IF NOT EXISTS users
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    email character varying(50),
    password character varying(120),
    username character varying(20),
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username)
);


CREATE TABLE IF NOT EXISTS roles
(
    id integer GENERATED ALWAYS AS IDENTITY,
    name character varying(20),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS user_roles
(
    user_id bigint NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
        REFERENCES roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


INSERT INTO users(email, password, username) VALUES ('admin@service.org', '$2a$12$hWGPaMFYtCLpP6dG53FAteD1THemZ1UT/LHueToMridxOz/deGFNK', 'admin');
INSERT INTO users(email, password, username) VALUES ('admin2@service.org', '$2a$12$hWGPaMFYtCLpP6dG53FAteD1THemZ1UT/LHueToMridxOz/deGFNK', 'admin2');
INSERT INTO users(email, password, username) VALUES ('user@service.org', '$2a$12$EId4XYpzCAxhLjCN1xW13.KKFI02nuhs0ijR3qAKR5davzsE53Qqq', 'user');
INSERT INTO users(email, password, username) VALUES ('oleg@service.org', '$2a$12$Kncdcixkr04o5t1clcLtQea4fu.2ppT2AX52XedW7wxPM1QZCjAu.', 'oleg');
INSERT INTO users(email, password, username) VALUES ('andrey@service.org', '$2a$12$wppNwkm2EG2w0a4wEfj.duEkmrq8Do3J1YV5RCyOV18IjE.ukhHtO', 'andrey');
INSERT INTO users(email, password, username) VALUES ('igor@service.org', '$2a$12$HwKhWHI/pBxN92ZkNzM2OOinwgM0QDGKOLJlfj3IGQ3WIuCQeTcEq', 'igor');
INSERT INTO users(email, password, username) VALUES ('alina@service.org', '$2a$12$wD5FczHMZ21Ahtm7cJX3BeV4xY4AepnbyL/q7xMi8aayaZvMyzJ5K', 'alina');

INSERT INTO roles(name) VALUES ('ROLE_USER');
INSERT INTO roles(name) VALUES ('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES ('ROLE_ADMIN');

INSERT INTO user_roles(user_id, role_id) VALUES (1, 3);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 3);
INSERT INTO user_roles(user_id, role_id) VALUES (3, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (4, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (5, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (6, 1);