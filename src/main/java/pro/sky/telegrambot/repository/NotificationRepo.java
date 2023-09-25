package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepo extends JpaRepository<NotificationTask, Long> {
    @Query(value = "SELECT * from notification_task WHERE date_time = :time" , nativeQuery = true)
    List<NotificationTask> getByDate_time(@Param("time") LocalDateTime localDateTime);

    @Query(value = "DELETE FROM notification_task WHERE date_time < CURRENT_DATE", nativeQuery = true)
    void deleteByDate();
}
