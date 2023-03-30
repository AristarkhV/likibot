package com.ts.compendium.telegram.bot.state;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import com.ts.compendium.telegram.bot.telegramchat.factories.KeyboardFactory;
import com.ts.compendium.telegram.bot.telegramchat.factories.MessageFactory;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.*;

@Component
@AllArgsConstructor
public class MainMenuChatState implements ChatState {

    private final MessageFactory messageFactory;
    private final KeyboardFactory keyboardFactory;

    @Override
    public ChatStateName getChatStateName() {
        return MAIN_MENU;
    }

    @Override
    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        likieTelegramBot.sendMessage(chatId, messageFactory.get("MAIN_MENU"), keyboardFactory.getMainMenuKeyboard());
    }

    @Override
    public ChatStateName processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        return MAIN_MENU;
    }
}
