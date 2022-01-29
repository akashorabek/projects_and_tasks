CREATE TABLE answers (
    id serial PRIMARY KEY,
    user_id int NOT NULL,
    topic_id int NOT NULL,
    message VARCHAR(250) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (user_id)
    REFERENCES users(id),
    FOREIGN KEY (topic_id)
    REFERENCES topics(id)
);