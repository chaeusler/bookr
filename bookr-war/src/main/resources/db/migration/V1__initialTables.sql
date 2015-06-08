CREATE TABLE bookr_authorization (
  principalName VARCHAR(255) NOT NULL,
  person_id     VARCHAR(255) NOT NULL,
  PRIMARY KEY (person_id)
);
ALTER TABLE bookr_authorization ADD CONSTRAINT uk_principalname UNIQUE (principalName);

CREATE TABLE bookr_authorization_role (
  authorization_id VARCHAR(255) NOT NULL,
  role             VARCHAR(255)
);

CREATE TABLE bookr_booking (
  id          VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  endTime     TIMESTAMP    NOT NULL,
  startTime   TIMESTAMP    NOT NULL,
  person_id   VARCHAR(255) NOT NULL,
  project_id  VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE bookr_password (
  authorization_id VARCHAR(255) NOT NULL,
  password         VARCHAR(255) NOT NULL,
  PRIMARY KEY (authorization_id)
);

CREATE TABLE bookr_person (
  id   VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
ALTER TABLE bookr_person ADD CONSTRAINT UK_NAME UNIQUE (name);

CREATE TABLE bookr_project (
  id   VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE bookr_project_person (
  project_id VARCHAR(255) NOT NULL,
  person_id  VARCHAR(255) NOT NULL,
  PRIMARY KEY (project_id, person_id)
);

ALTER TABLE bookr_authorization ADD CONSTRAINT fk_authorization_person FOREIGN KEY (person_id) REFERENCES bookr_person;
ALTER TABLE bookr_authorization_role ADD CONSTRAINT fk_authorization_role_authorization FOREIGN KEY (authorization_id) REFERENCES bookr_authorization;
ALTER TABLE bookr_booking ADD CONSTRAINT fk_booking_person FOREIGN KEY (person_id) REFERENCES bookr_person;
ALTER TABLE bookr_booking ADD CONSTRAINT fk_booking_project FOREIGN KEY (project_id) REFERENCES bookr_project;
ALTER TABLE bookr_password ADD CONSTRAINT fk_password_authorization FOREIGN KEY (authorization_id) REFERENCES bookr_authorization;
ALTER TABLE bookr_project_person ADD CONSTRAINT fk_project_person_person FOREIGN KEY (person_id) REFERENCES bookr_person;
ALTER TABLE bookr_project_person ADD CONSTRAINT fk_project_person_project FOREIGN KEY (project_id) REFERENCES bookr_project;