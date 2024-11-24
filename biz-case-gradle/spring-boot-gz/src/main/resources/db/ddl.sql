CREATE TABLE t_user
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    trueName VARCHAR(255) NOT NULL
);