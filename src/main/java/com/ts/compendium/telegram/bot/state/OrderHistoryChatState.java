package com.ts.compendium.telegram.bot.state;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import com.ts.compendium.telegram.bot.telegramchat.factories.MessageFactory;
import com.ts.compendium.telegram.bot.telegramchat.factories.OrderHistoryFactory;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.*;
import static com.ts.compendium.telegram.bot.enums.TelegramButton.*;

@Component
@AllArgsConstructor
public class OrderHistoryChatState implements ChatState {

    private final OrderHistoryFactory orderHistoryFactory;

    @Override
    public ChatStateName getChatStateName() {
        return ORDER_HISTORY;
    }

    @Override
    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        likieTelegramBot.sendMessage(chatId, orderHistoryFactory.getOrderHistory(chatId), new ReplyKeyboardRemove(true));
    }

    @Override
    public ChatStateName processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        return LOCATION_REQUEST;
    }
}
