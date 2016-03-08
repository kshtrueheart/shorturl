CREATE TABLE urls (
  id           VARCHAR(36)  NOT NULL,
  url          VARCHAR(255) NOT NULL,
  code         CHAR(6)      NOT NULL UNIQUE,
  created_time TIMESTAMP    NOT NULL,
  PRIMARY KEY (id)
);