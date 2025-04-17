CREATE TABLE usermodel (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255)
);
ALTER TABLE taskmodel ADD COLUMN user_id BIGINT;
ALTER TABLE taskmodel
    ADD CONSTRAINT fk_task_user FOREIGN KEY (user_id) REFERENCES usermodel(id);
