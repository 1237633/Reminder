package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.NotificationTask;

public interface NotificationRepo extends JpaRepository<NotificationTask, Long> {

}
