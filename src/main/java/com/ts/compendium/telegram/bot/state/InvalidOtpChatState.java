package com.ts.compendium.telegram.bot.state;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import com.ts.compendium.telegram.bot.otp.OtpService;
import com.ts.compendium.telegram.bot.telegramchat.factories.KeyboardFactory;
import com.ts.compendium.telegram.bot.telegramchat.factories.MessageFactory;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.AUTHORIZED;
import static com.ts.compendium.telegram.bot.enums.ChatStateName.INVALID_OTP;

@Component
@AllArgsConstructor
public class InvalidOtpChatState implements ChatState{

    private final MessageFactory messageFactory;
    private final KeyboardFactory keyboardFactory;
    private final OtpService otpService;

    @Override
    public ChatStateName getChatStateName() {
        return INVALID_OTP;
    }

    @Override
    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        String userNumber = otpService.getNumberByChatId(chatId);
        likieTelegramBot.sendMessage(chatId, messageFactory.get("INVALID_OTP", userNumber),
                keyboardFactory.getChangeContactKeyboard());
    }

    @Override
    public ChatStateName processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        String userNumber = otpService.getNumberByChatId(chatId);
        if (otpService.isOtpValid(userNumber, text)) {
            likieTelegramBot.sendMessage(chatId, messageFactory.get("AUTHORIZED"));
            return AUTHORIZED;
        } else {
            return INVALID_OTP;
        }
    }
}
