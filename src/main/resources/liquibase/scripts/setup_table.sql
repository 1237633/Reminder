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



