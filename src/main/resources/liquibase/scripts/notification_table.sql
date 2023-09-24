-- liquibase formatted sql
-- changeset vasyan:1

CREATE TABLE Notification_tasks
(
    task_id      bigint PRIMARY KEY,
    chat_id      bigint    NOT NULL CHECK ( chat_id >= 0 ),
    notification text      NOT NULL,
    date_time    timestamp NOT NULL check ( date_time >= CURRENT_TIMESTAMP )
)
