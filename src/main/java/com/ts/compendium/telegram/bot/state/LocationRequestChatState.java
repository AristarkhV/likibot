package com.ts.compendium.telegram.bot.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.compendium.telegram.bot.chatsession.ChatSessionEntity;
import com.ts.compendium.telegram.bot.chatsession.ChatSessionService;
import com.ts.compendium.telegram.bot.city.City;
import com.ts.compendium.telegram.bot.city.CityService;
import com.ts.compendium.telegram.bot.drug.Locale;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.ts.compendium.telegram.bot.LikieTelegramBot;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import com.ts.compendium.telegram.bot.telegramchat.factories.KeyboardFactory;
import com.ts.compendium.telegram.bot.telegramchat.factories.MessageFactory;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.*;

@Slf4j
@Component
@AllArgsConstructor
public class LocationRequestChatState implements ChatState {

    private final KeyboardFactory keyboardFactory;
    private final MessageFactory messageFactory;
    private final CityService cityService;
    private final ChatSessionService chatSessionService;

    @Override
    public ChatStateName getChatStateName() {
        return LOCATION_REQUEST;
    }

    @Override
    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        likieTelegramBot.sendMessage(chatId, messageFactory.get("LOCATION_REQUEST"), keyboardFactory.getLocationRequestKeyboard());
    }

    @Override
    public ChatStateName processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Location location = objectMapper.readValue(text, Location.class);
            City city = cityService.cityByCoordinates(Locale.UA, location.getLatitude(), location.getLongitude());
            ChatSessionEntity chatSession = chatSessionService.findChatSession(chatId);
            chatSession.setCity(city);
            chatSessionService.save(chatSession);
            return ORDER_HISTORY;
        } catch (JsonProcessingException e) {
            log.error("Can't read location", e);
            return UNKNOWN_REQUEST;
        }
    }
}
