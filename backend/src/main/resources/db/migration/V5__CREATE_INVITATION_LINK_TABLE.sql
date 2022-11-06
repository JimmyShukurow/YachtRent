CREATE TABLE invitation_links
  (
     id                  SERIAL PRIMARY KEY,
     hash                VARCHAR(255),
     expire_at           TIMESTAMP,
     created_at          TIMESTAMP,
     deleted_at          TIMESTAMP

  );