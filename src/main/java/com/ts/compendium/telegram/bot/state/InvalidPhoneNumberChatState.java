package com.ts.compendium.telegram.bot.state;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import com.ts.compendium.telegram.bot.telegramchat.factories.MessageFactory;
import com.ts.compendium.telegram.bot.utils.MobilePhoneNumberValidatorUtils;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.*;

@Component
@AllArgsConstructor
public class InvalidPhoneNumberChatState implements ChatState {

    private MessageFactory messageFactory;

    @Override
    public ChatStateName getChatStateName() {
        return INVALID_PHONE_NUMBER;
    }

    @Override
    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        likieTelegramBot.sendMessage(chatId, messageFactory.get("INVALID_NUMBER"));
    }

    @Override
    public ChatStateName processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        if (MobilePhoneNumberValidatorUtils.isValidMobileNo(text)) {
            return NEED_AUTHORIZATION;
        } else {
            return INVALID_PHONE_NUMBER;
        }
    }
}
