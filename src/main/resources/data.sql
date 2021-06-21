INSERT INTO roles
VALUES(1, 'ROLE_ADMIN');

INSERT INTO roles
VALUES(2, 'ROLE_USER');

INSERT INTO users (user_id, fullname, email, password)
VALUES('101', 'Rezaur Rahman', 'rezaur@gmail.com', 'iamadmin');

INSERT INTO users (user_id, fullname, email, password)
VALUES('102', 'Sanzida Sultana', 'sanzida@gmail.com', 'iamuser');

INSERT INTO user_role
VALUES('101', 1);

INSERT INTO user_role
VALUES('101', 2);

INSERT INTO user_role
VALUES('102', 2);