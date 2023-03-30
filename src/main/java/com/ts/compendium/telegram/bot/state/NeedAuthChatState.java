package com.ts.compendium.telegram.bot.state;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import com.ts.compendium.telegram.bot.otp.OtpService;
import com.ts.compendium.telegram.bot.telegramchat.factories.KeyboardFactory;
import com.ts.compendium.telegram.bot.telegramchat.factories.MessageFactory;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.*;

@Component
@AllArgsConstructor
public class NeedAuthChatState implements ChatState {

    private final OtpService otpService;
    private final MessageFactory messageFactory;
    private final KeyboardFactory keyboardFactory;

    @Override
    public ChatStateName getChatStateName() {
        return NEED_AUTHORIZATION;
    }

    @Override
    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        otpService.sendOtp(text, chatId);
        likieTelegramBot.sendMessage(chatId, messageFactory.get("SMS_SENT", text), new ReplyKeyboardRemove(true));
    }

    @Override
    public ChatStateName processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        String userNumber = otpService.getNumberByChatId(chatId);
        if (otpService.isOtpValid(userNumber, text)) {
            return AUTHORIZED;
        } else {
            return INVALID_OTP;
        }
    }
}
