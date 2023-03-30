package com.ts.compendium.telegram.bot.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import com.ts.compendium.telegram.bot.telegramchat.factories.KeyboardFactory;
import com.ts.compendium.telegram.bot.telegramchat.factories.MessageFactory;
import com.ts.compendium.telegram.bot.utils.MobilePhoneNumberValidatorUtils;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.CONTACT_REQUEST;
import static com.ts.compendium.telegram.bot.enums.ChatStateName.INVALID_PHONE_NUMBER;
import static com.ts.compendium.telegram.bot.enums.ChatStateName.NEED_AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class ContactRequestChatState implements ChatState {

    private final MessageFactory messageFactory;
    private final KeyboardFactory keyboardFactory;

    @Override
    public ChatStateName getChatStateName() {
        return CONTACT_REQUEST;
    }

    @Override
    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        likieTelegramBot.sendMessage(chatId,
                messageFactory.get("PHONE_NUMBER_REQUEST"),
                keyboardFactory.getRequestContactKeyboard());
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
