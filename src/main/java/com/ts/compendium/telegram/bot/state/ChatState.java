package com.ts.compendium.telegram.bot.state;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;


public interface ChatState {

    ChatStateName getChatStateName();

    void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException;

    ChatStateName processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException;
}
