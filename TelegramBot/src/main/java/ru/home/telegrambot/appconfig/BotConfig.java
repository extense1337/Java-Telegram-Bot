package ru.home.telegrambot.appconfig;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.home.telegrambot.MyTelegramBot;
import ru.home.telegrambot.botapi.TelegramFront;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {

    private String webhookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public MyTelegramBot myTelegramBot(TelegramFront telegramFront) {

        MyTelegramBot myTelegramBot = new MyTelegramBot(telegramFront);
        myTelegramBot.setBotUserName(botUserName);
        myTelegramBot.setBotToken(botToken);
        myTelegramBot.setWebhookPath(webhookPath);

        return myTelegramBot;
    }

    @Bean
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
