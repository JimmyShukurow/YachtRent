CREATE TABLE yachts
  (
     id                  SERIAL PRIMARY KEY,
     name                VARCHAR(255),
     type_id             INTEGER,
     price               NUMERIC,
     crew                VARCHAR(225),
     sleeping_capacity   INTEGER,
     cruise_capacity     INTEGER,
     created_at          TIMESTAMP,
     deleted_at          TIMESTAMP

  );

CREATE TABLE users (
       id                  SERIAL PRIMARY KEY,
       username            VARCHAR(255),
       first_name          VARCHAR(255),
       last_name           VARCHAR(255),
       password            VARCHAR(255),
       token               VARCHAR(255),
       created_at          TIMESTAMP,
       deleted_at          TIMESTAMP
);

CREATE TABLE roles (
       id                  SERIAL PRIMARY KEY,
       name                VARCHAR(255)
);

CREATE TABLE yacht_types (
       id                  SERIAL PRIMARY KEY,
       name                VARCHAR(255)
);

CREATE TABLE user_roles (
       id                   SERIAL PRIMARY KEY,
       user_id              INTEGER,
       role_id              INTEGER
)

