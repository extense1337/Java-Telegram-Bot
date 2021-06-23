package ru.home.telegrambot.botapi.handlers.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.telegrambot.botapi.BotState;
import ru.home.telegrambot.botapi.InputMessageHandler;
import ru.home.telegrambot.model.InterestingFactsData;
import ru.home.telegrambot.service.InterestingFactsDataService;
import ru.home.telegrambot.service.MainMenuService;

import java.util.List;

@Component
public class InterestingFactHandler implements InputMessageHandler {
    private MainMenuService mainMenuService;
    private InterestingFactsDataService factsDataService;
    private List<InterestingFactsData> facts;
    private int factId;

    public InterestingFactHandler(MainMenuService mainMenuService,
                                  InterestingFactsDataService factsDataService) {
        this.mainMenuService = mainMenuService;
        this.factsDataService = factsDataService;
        facts = factsDataService.getAllFacts();
        factId = -1;
    }

    @Override
    public SendMessage handle(Message message) {
        String currentFact = nextFact().getFact();
        return mainMenuService.getMainMenuMessage(message.getChatId(),
                currentFact);
    }

    public InterestingFactsData nextFact() {
        factId++;
        if (factId >= facts.size()) {
            factId = 0;
        }
        return facts.get(factId);
    }


    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_INTERESTING_FACT;
    }
}
