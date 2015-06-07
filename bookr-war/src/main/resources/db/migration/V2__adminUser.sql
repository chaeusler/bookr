INSERT INTO bookr_person ("id", "name")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'administrator');

INSERT INTO bookr_authorization ("person_id", "principalname")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'administrator');

INSERT INTO bookr_authorization_role ("authorization_id", "role")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'ADMINISTRATOR');

INSERT INTO bookr_authorization_role ("authorization_id", "role")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'USER');

INSERT INTO bookr_password ("authorization_id", "password")
VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'administrator');