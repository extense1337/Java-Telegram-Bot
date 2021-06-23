package ru.home.telegrambot;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.telegrambot.botapi.TelegramFront;

public class MyTelegramBot extends TelegramWebhookBot {

    private String webhookPath;
    private String botUserName;
    private String botToken;

    private TelegramFront telegramFront;

    public MyTelegramBot(TelegramFront telegramFront) {
        this.telegramFront = telegramFront;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        final BotApiMethod<?> replyMessageToUser = telegramFront.handleUpdate(update);

        return replyMessageToUser;
    }

    @Override
    public String getBotPath() {
        return webhookPath;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setWebhookPath(String webhookPath) {
        this.webhookPath = webhookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
