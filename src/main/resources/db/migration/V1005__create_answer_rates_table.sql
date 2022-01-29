CREATE TABLE answer_rates (
    id serial PRIMARY KEY,
    user_id int NOT NULL,
    answer_id int NOT NULL,
    liked BOOLEAN NOT NULL,
    disliked BOOLEAN NOT NULL,
    FOREIGN KEY (user_id)
    REFERENCES users(id),
    FOREIGN KEY (answer_id)
    REFERENCES answers(id)
);