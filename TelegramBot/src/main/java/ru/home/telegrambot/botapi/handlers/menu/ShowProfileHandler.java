package ru.home.telegrambot.botapi.handlers.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.telegrambot.botapi.BotState;
import ru.home.telegrambot.botapi.InputMessageHandler;
import ru.home.telegrambot.model.UserProfileData;
import ru.home.telegrambot.cache.UserDataCache;
import ru.home.telegrambot.service.UsersProfileDataService;

@Slf4j
@Component
public class ShowProfileHandler implements InputMessageHandler {

    private UserDataCache userDataCache;
    private UsersProfileDataService profileDataService;

    public ShowProfileHandler(UserDataCache userDataCache, UsersProfileDataService profileDataService) {
        this.userDataCache = userDataCache;
        this.profileDataService = profileDataService;
    }

    @Override
    public SendMessage handle(Message message) {
        SendMessage userReply;
        final int userId = message.getFrom().getId();
        final UserProfileData profileData = profileDataService.getUserProfileData(message.getChatId());

        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);

        if (profileData != null) {
            userReply = new SendMessage(message.getChatId(),
                    String.format("%s%n-------------------%n%s", "Данные вашего профиля:", profileData));
        } else {
            userReply = new SendMessage(message.getChatId(), "Профиль не найден.");
            log.warn("Could not found profileData for chatId: " + message.getChatId());
        }

        return userReply;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_USER_PROFILE;
    }
}
