package com.ts.compendium.telegram.bot.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.CONTACT_REQUEST;
import static com.ts.compendium.telegram.bot.enums.ChatStateName.START;

@Component
public class StartChatState implements ChatState {

    @Override
    public ChatStateName getChatStateName() {
        return START;
    }

    @Override
    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
    }

    @Override
    public ChatStateName processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        return CONTACT_REQUEST;
    }
}
