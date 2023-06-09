package com.ts.compendium.telegram.bot.state;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import com.ts.compendium.telegram.bot.telegramchat.factories.KeyboardFactory;
import com.ts.compendium.telegram.bot.telegramchat.factories.MessageFactory;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.UNKNOWN_REQUEST;

@Component
@AllArgsConstructor
public class UnknownRequestChatState implements ChatState {

    private final MessageFactory messageFactory;
    private final KeyboardFactory keyboardFactory;

    @Override
    public ChatStateName getChatStateName() {
        return UNKNOWN_REQUEST;
    }

    @Override
    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        //FIXME: hide location button
        likieTelegramBot.sendMessage(chatId, messageFactory.get("UNKNOWN_MESSAGE"), keyboardFactory.getMainMenuKeyboard());
    }

    @Override
    public ChatStateName processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        return UNKNOWN_REQUEST;
    }
}
