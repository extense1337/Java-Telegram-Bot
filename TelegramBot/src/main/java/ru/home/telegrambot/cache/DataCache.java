package ru.home.telegrambot.cache;

import ru.home.telegrambot.botapi.BotState;
import ru.home.telegrambot.model.UserProfileData;

public interface DataCache {

    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    UserProfileData getUserProfileData(int userId);

    void saveUserProfileData(int userId, UserProfileData userProfileData);
}
