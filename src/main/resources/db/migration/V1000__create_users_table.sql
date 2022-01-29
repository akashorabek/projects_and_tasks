CREATE TABLE users (
    id serial PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    enabled boolean NOT NULL
);