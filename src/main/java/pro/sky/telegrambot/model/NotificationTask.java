package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue
    long task_id;
    long chat_id;
    String notification;
    LocalDateTime date_time;

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return getTask_id() == that.getTask_id() && getChat_id() == that.getChat_id() && getNotification().equals(that.getNotification()) && getDate_time().equals(that.getDate_time());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTask_id(), getChat_id(), getNotification(), getDate_time());
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "task_id=" + task_id +
                ", chat_id=" + chat_id +
                ", notification='" + notification + '\'' +
                ", date_time=" + date_time +
                '}';
    }
}
