INSERT INTO app_user
VALUES('10595', 'iamadmin');

INSERT INTO app_user
VALUES('10597', 'iamfaculty');

INSERT INTO role
VALUES(1, 'ADMIN');

INSERT INTO role
VALUES(2, 'FACULTY');

INSERT INTO user_role
VALUES('10595', 1);

INSERT INTO user_role
VALUES('10597', 2);