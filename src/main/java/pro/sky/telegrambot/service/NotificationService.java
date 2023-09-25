package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private NotificationRepo notificationRepo;
    private Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private Matcher matcher;
    private final Pattern pattern = Pattern.compile("([0-9.:\\s]{16})(\\s)([А-Яа-яЁё\\w].+)");


    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    public void add(String inputMessage, long chatId) throws IllegalStateException, DataIntegrityViolationException {
        NotificationTask notificationTask = parseAndCreate(inputMessage);
        notificationTask.setChat_id(chatId);
        notificationRepo.save(notificationTask);
        logger.info("Added notification task {}", notificationTask);
    }

    public NotificationTask parseAndCreate(String inputMessage) throws IllegalStateException {
        logger.info("Parsing {}", inputMessage);
        matcher = pattern.matcher(inputMessage);
        String dateAndTime;
        String notificationText;

        if (matcher.matches()) {
            dateAndTime = matcher.group(1);
            notificationText = matcher.group(3);
        } else {
            throw new IllegalStateException("No match found");
        }

        NotificationTask notificationTask = new NotificationTask();
        notificationTask.setDate_time(LocalDateTime.parse(dateAndTime, DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm")));
        notificationTask.setNotification(notificationText);

        return notificationTask;
    }


    public Map<Long, Set<String>> getNotificationsToSend() {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> notificationTasks = notificationRepo.getByDate_time(time);
        logger.info("Getting notifications for " + time);
        return notificationTasks.stream()
                .collect(Collectors
                        .groupingBy(e -> e.getChat_id(), Collectors.mapping(NotificationTask::getNotification, Collectors.toSet())));
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldTasks() {
        notificationRepo.deleteByDate();
        logger.info("Deleted old tasks");
    }
}
