INSERT INTO BOOKR_PERSON ("id", "name")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'administrator');

INSERT INTO BOOKR_AUTHORIZATION ("person_id", "principalname")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'administrator');

INSERT INTO BOOKR_AUTHORIZATION_ROLE ("authorization_id", "role")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'ADMINISTRATOR');

INSERT INTO BOOKR_AUTHORIZATION_ROLE ("authorization_id", "role")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'USER');

INSERT INTO BOOKR_PASSWORD ("authorization_id", "password")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'administrator');