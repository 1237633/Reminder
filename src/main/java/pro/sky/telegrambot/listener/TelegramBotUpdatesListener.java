package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.NotificationService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    private SendMessage message;
    @Autowired
    private NotificationService notificationService;


    @Value("Саламалейкум!")
    private String startMsg;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message().text().equalsIgnoreCase("/start")) {
                message = new SendMessage(update.message().chat().id(), startMsg);
                telegramBot.execute(message);
                System.out.println(startMsg);
            }

            if (update.message().text().equalsIgnoreCase("/get")) {
                System.out.println(notificationService.get());
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
