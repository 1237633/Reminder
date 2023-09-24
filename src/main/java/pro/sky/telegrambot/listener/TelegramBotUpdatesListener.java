package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import pro.sky.telegrambot.service.NotificationService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private NotificationService notificationService;


    @Value("Саламалейкум!")
    private String startMsg;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @PreDestroy
    public void close() {
        telegramBot.setUpdatesListener(null);
        telegramBot.shutdown();
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message() != null) {
                if (update.message().text().equalsIgnoreCase("/start")) {
                    telegramBot.execute(new SendMessage(update.message().chat().id(), startMsg));
                    return;
                }


                try {
                    notificationService.add(update.message().text(), update.message().chat().id());
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Атлишна! Напоминалка добавлена!"));
                } catch (IllegalStateException e) {
                    logger.error(e.getMessage() + " for " + update.message().text());
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Не могу распознать команду. Введите напоминание в формате: дд.мм.гггг чч:мм 'текст напоминания'. Дата и время должны быть больше текущих!"));
                } catch (DataIntegrityViolationException e) {
                    logger.error(e.getMessage() + " for " + update.message().text());
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Вы уже создали такое напоминание, либо указанное время уже прошло."));
                }
            } else {
                throw new NullPointerException("No message present");
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
