package ru.home.telegrambot.botapi.handlers.askfact;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.telegrambot.botapi.BotState;
import ru.home.telegrambot.botapi.InputMessageHandler;
import ru.home.telegrambot.service.ReplyMessagesService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AskInterestingFactHandler implements InputMessageHandler {

    private ReplyMessagesService messagesService;

    public AskInterestingFactHandler(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_INTERESTING_FACT;
    }

    private SendMessage processUsersInput(Message inputMsg) {

        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.askInterestingFact");
        replyToUser.setReplyMarkup(getInlineMessageButtons());

        return replyToUser;
    }
    private InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton().setText("Да");
        InlineKeyboardButton buttonNo = new InlineKeyboardButton().setText("Пока нет");

        //Every button must have callBackData, or else not work !
        buttonYes.setCallbackData("buttonYes");
        buttonNo.setCallbackData("buttonNo");

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(buttonYes);
        keyboardButtonsRow.add(buttonNo);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
