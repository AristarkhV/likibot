package com.ts.compendium.telegram.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.ts.compendium.telegram.bot.telegramchat.ChatSessionEntity;
import com.ts.compendium.telegram.bot.telegramchat.ChatSessionRepository;
import com.ts.compendium.telegram.bot.telegramchat.factories.MessageFactory;

import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static com.ts.compendium.telegram.bot.enums.ChatStateName.*;

@Component
@Slf4j
@AllArgsConstructor
public class LikieTelegramBot extends TelegramLongPollingBot {

    private final ChatSessionRepository chatSessionRepository;

    private final ChatStateFactory chatStateFactory;

    private final MessageFactory messageFactory;

    @Override
    public String getBotToken() {
        return "1773432403:AAEw-qk5cjZCsHsNCy9M01bbru_c4iI7Lbk";
    }

    @Override
    public String getBotUsername() {
        return "LazyBotinok_bot";
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
        ChatSessionEntity chatSessionEntity = findChatSession(chatId);
        overwriteStepByText(text, chatSessionEntity);
        ChatState currentState = chatStateFactory.getStateByName(chatSessionEntity.getStateName());
        ChatStateName nextStepName = currentState.processRequest(chatId, text, this);
        ChatState nextState = chatStateFactory.getStateByName(nextStepName);
        nextState.preProcess(chatId, text, this);
        chatSessionEntity.setStateName(nextStepName);
        chatSessionRepository.save(chatSessionEntity);
    }

    private ChatSessionEntity findChatSession(Long chatId) throws TelegramApiException {
        Optional<ChatSessionEntity> chatSessionEntityOptional = chatSessionRepository.findById(chatId);
        if (chatSessionEntityOptional.isEmpty()) {
            ChatSessionEntity chatSessionEntity = new ChatSessionEntity();
            chatSessionEntity.setChatId(chatId);
            chatSessionEntity.setStateName(START);
            chatSessionRepository.save(chatSessionEntity);
            sendMessage(chatId, messageFactory.get("START"));
            return chatSessionEntity;
        } else {
            return chatSessionEntityOptional.get();
        }
    }

    private void overwriteStepByText(String text, ChatSessionEntity chatSessionEntity) {
        switch (text) {
            case "/help" -> chatSessionEntity.setStateName(HELP);
            case "/main-menu" -> chatSessionEntity.setStateName(MAIN_MENU);
            case "/start" -> chatSessionEntity.setStateName(START);
            case "/order-drugs" -> chatSessionEntity.setStateName(ORDER);
            case "/order-history" -> chatSessionEntity.setStateName(ORDER_HISTORY);
            case "/location-request" -> chatSessionEntity.setStateName(LOCATION_REQUEST);
            default -> ifUnExpectedData(chatSessionEntity);
        }
    }

    private void ifUnExpectedData(ChatSessionEntity chatSessionEntity) {
        if (!(CONTACT_REQUEST.equals(chatSessionEntity.getStateName())
                || LOCATION_REQUEST.equals(chatSessionEntity.getStateName())
                || INVALID_PHONE_NUMBER.equals(chatSessionEntity.getStateName())
                || NEED_AUTHORIZATION.equals(chatSessionEntity.getStateName())
                || INVALID_OTP.equals(chatSessionEntity.getStateName()))) {
            chatSessionEntity.setStateName(UNKNOWN_REQUEST);
        }
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
