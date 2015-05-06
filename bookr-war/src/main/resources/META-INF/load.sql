INSERT INTO BOOKR_PERSON("ID", "PRINCIPALNAME") VALUES ('3811f110-6601-4584-8aff-6c242dd25704', 'user');
INSERT INTO BOOKR_PERSON("ID", "PRINCIPALNAME") VALUES ('eb211156-c010-4431-9587-f5e1382e9ed2', 'manager');
INSERT INTO BOOKR_PERSON("ID", "PRINCIPALNAME") VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'administrator');

INSERT INTO BOOKR_PASSWORD("PERSON_ID", "PASSWORD") VALUES ('3811f110-6601-4584-8aff-6c242dd25704', 'user');
INSERT INTO BOOKR_PASSWORD("PERSON_ID", "PASSWORD") VALUES ('eb211156-c010-4431-9587-f5e1382e9ed2', 'manager');
INSERT INTO BOOKR_PASSWORD("PERSON_ID", "PASSWORD") VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'administrator');

INSERT INTO BOOKR_ROLE("PERSON_ID", "TYPE") VALUES ('3811f110-6601-4584-8aff-6c242dd25704', 'USER');
INSERT INTO BOOKR_ROLE("PERSON_ID", "TYPE") VALUES ('eb211156-c010-4431-9587-f5e1382e9ed2', 'MANAGER');
INSERT INTO BOOKR_ROLE("PERSON_ID", "TYPE") VALUES ('eb211156-c010-4431-9587-f5e1382e9ed2', 'USER');
INSERT INTO BOOKR_ROLE("PERSON_ID", "TYPE") VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'ADMINISTRATOR');
INSERT INTO BOOKR_ROLE("PERSON_ID", "TYPE") VALUES ('50fd4f99-8295-4f51-b887-4e88e4c4236b', 'USER');

INSERT INTO BOOKR_PROJECT("ID", "NAME") VALUES ('c4eecdf5-7667-4f75-946d-7c954c13b25d', 'ultimate project');
INSERT INTO BOOKR_PROJECT("ID", "NAME") VALUES ('ea1de65b-cfcc-4312-8c2b-2c1d142a9f79', 'weird project');
INSERT INTO BOOKR_PROJECT("ID", "NAME") VALUES ('008c93bd-57fa-43ca-b8cb-374a15b6e613', 'funny project');

INSERT INTO BOOKR_BOOKING("ID", "PROJECT_ID", "PERSON_ID", "DESCRIPTION", "START", "END") VALUES ('82d626aa-c4db-484d-b21b-47c176b35a2b', 'c4eecdf5-7667-4f75-946d-7c954c13b25d', '3811f110-6601-4584-8aff-6c242dd25704', 'ultimate - user', CURRENT_DATE(), CURRENT_DATE());
INSERT INTO BOOKR_BOOKING("ID", "PROJECT_ID", "PERSON_ID", "DESCRIPTION", "START", "END") VALUES ('0896cd3a-c735-40d5-b309-51f06fbaab68', 'ea1de65b-cfcc-4312-8c2b-2c1d142a9f79', 'eb211156-c010-4431-9587-f5e1382e9ed2', 'weird - manager', CURRENT_DATE(), CURRENT_DATE());
INSERT INTO BOOKR_BOOKING("ID", "PROJECT_ID", "PERSON_ID", "DESCRIPTION", "START", "END") VALUES ('e034a887-8e10-4ec4-a4e9-a415d262eaa3', 'ea1de65b-cfcc-4312-8c2b-2c1d142a9f79', '3811f110-6601-4584-8aff-6c242dd25704', 'weird - user', CURRENT_DATE(), CURRENT_DATE());
INSERT INTO BOOKR_BOOKING("ID", "PROJECT_ID", "PERSON_ID", "DESCRIPTION", "START", "END") VALUES ('4660d23c-6bde-4ed0-be78-15cfeb972336', 'c4eecdf5-7667-4f75-946d-7c954c13b25d', 'eb211156-c010-4431-9587-f5e1382e9ed2', 'ultimate - manager', CURRENT_DATE(), CURRENT_DATE());