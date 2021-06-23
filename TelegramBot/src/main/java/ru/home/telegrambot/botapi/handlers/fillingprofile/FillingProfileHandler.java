package ru.home.telegrambot.botapi.handlers.fillingprofile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.telegrambot.botapi.BotState;
import ru.home.telegrambot.botapi.InputMessageHandler;
import ru.home.telegrambot.cache.UserDataCache;
import ru.home.telegrambot.model.UserProfileData;
import ru.home.telegrambot.service.ReplyMessagesService;
import ru.home.telegrambot.service.UsersProfileDataService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FillingProfileHandler implements InputMessageHandler {

    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    private UsersProfileDataService profileDataService;

    public FillingProfileHandler(UserDataCache userDataCache,
                                 ReplyMessagesService messagesService,
                                 UsersProfileDataService profileDataService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.profileDataService = profileDataService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_NAME);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_NAME)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askName");
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_AGE);
        }

        if (botState.equals(BotState.ASK_AGE)) {
            profileData.setName(usersAnswer);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askAge");
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_GENDER);
        }

        if (botState.equals(BotState.ASK_GENDER)) {
            profileData.setAge(Integer.parseInt(usersAnswer));
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askGender");
            replyToUser.setReplyMarkup(getGenderButtonsMarkup());
        }

        if (botState.equals(BotState.PROFILE_FILLED)) {
            profileData.setChatId(chatId);
            profileDataService.saveUserProfileData(profileData);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.profileFilled");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }

        userDataCache.saveUserProfileData(userId, profileData);

        return replyToUser;
    }
    private InlineKeyboardMarkup getGenderButtonsMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonGenderMan = new InlineKeyboardButton().setText("М");
        InlineKeyboardButton buttonGenderWoman = new InlineKeyboardButton().setText("Ж");

        //Every button must have callBackData, or else not work !
        buttonGenderMan.setCallbackData("buttonMan");
        buttonGenderWoman.setCallbackData("buttonWoman");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonGenderMan);
        keyboardButtonsRow1.add(buttonGenderWoman);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
