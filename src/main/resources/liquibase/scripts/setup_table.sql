-- liquibase formatted sql
-- changeset vasyan:1

CREATE TABLE IF NOT EXISTS Notification_task
(
    task_id      bigint PRIMARY KEY,
    chat_id      bigint    NOT NULL CHECK ( chat_id >= 0 ),
    notification text      NOT NULL,
    date_time    timestamp NOT NULL check ( date_time >= CURRENT_TIMESTAMP )
);

-- changeset vasyan:2

ALTER TABLE Notification_task
    ADD CONSTRAINT unique_notification_and_date UNIQUE (notification, date_time);

-- changeset vasyan:3

ALTER TABLE Notification_task
    DROP CONSTRAINT unique_notification_and_date;

ALTER TABLE Notification_task
    ADD CONSTRAINT unique_notification_and_date UNIQUE (notification, date_time, chat_id);

-- changeset vasyan:4

CREATE INDEX notification_chat_id ON notification_task (notification, chat_id);

-- changeset vasyan:5

DROP INDEX notification_chat_id;

CREATE INDEX date_time ON notification_task (date_time);




