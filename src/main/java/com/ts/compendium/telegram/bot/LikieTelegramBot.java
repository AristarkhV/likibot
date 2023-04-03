package com.ts.compendium.telegram.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.compendium.telegram.bot.chatsession.ChatSessionEntity;
import com.ts.compendium.telegram.bot.chatsession.ChatSessionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import com.ts.compendium.telegram.bot.state.ChatState;
import com.ts.compendium.telegram.bot.state.ChatStateFactory;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@Slf4j
@AllArgsConstructor
public class LikieTelegramBot extends TelegramLongPollingBot {

    private final ChatStateFactory chatStateFactory;
    private final ChatSessionService chatSessionService;

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                processMessage(update.getMessage().getChatId(), getText(update.getMessage()));
            } else if (update.hasCallbackQuery()) {
                processMessage(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getData());
            }
        } catch (TelegramApiRequestException e) {
            log.error("[onUpdateReceived] {}", e.getApiResponse(), e);
            sendErrorBack(update, "Виникла помилка: " + e.getApiResponse());
        } catch (Exception e) {
            log.error("[onUpdateReceived]", e);
            sendErrorBack(update, "Виникла помилка: " + e.getMessage());
        }
    }

    private String getText(Message message) {
        String text = message.getText();
        if (message.getContact() != null && isBlank(text)) {
            text = message.getContact().getPhoneNumber();
        } else if (message.getLocation() != null && isBlank(text)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                text = objectMapper.writeValueAsString(message.getLocation());
            } catch (JsonProcessingException e) {
                log.error("Can't read location", e);
            }
        }
        return text;
    }

    private void processMessage(Long chatId, String text) throws TelegramApiException {
        ChatSessionEntity chatSessionEntity = chatSessionService.findChatSession(chatId);
        chatSessionService.overwriteStepByText(text, chatSessionEntity);
        ChatState currentState = chatStateFactory.getStateByName(chatSessionEntity.getStateName());
        ChatStateName nextStepName = currentState.processRequest(chatId, text, this);
        ChatState nextState = chatStateFactory.getStateByName(nextStepName);
        nextState.preProcess(chatId, text, this);
        chatSessionService.setNextStep(chatId, nextStepName);
    }

    private void sendErrorBack(Update update, String message) {
        try {
            sendMessage(getChatId(update), message);
        } catch (TelegramApiRequestException e) {
            log.error("[onUpdateReceived] {}", e.getApiResponse(), e);
        } catch (TelegramApiException e) {
            log.error("[onUpdateReceived]", e);
        }
    }

    private Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        } else {
            throw new RuntimeException("Chat ID not found");
        }
    }

    public void sendMessage(Long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        execute(sendMessage);
    }

    public void sendMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboard) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(inlineKeyboard);
        execute(sendMessage);
    }

    public void sendMessage(Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        execute(sendMessage);
    }

    public void sendMessage(Long chatId, String text, ReplyKeyboardRemove replyKeyboardRemove) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        execute(sendMessage);
    }
}
