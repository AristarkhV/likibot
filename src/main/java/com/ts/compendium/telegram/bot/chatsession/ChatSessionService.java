package com.ts.compendium.telegram.bot.chatsession;

import com.ts.compendium.telegram.bot.city.City;
import com.ts.compendium.telegram.bot.city.CityService;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ts.compendium.telegram.bot.enums.ChatStateName.*;

@Service
@AllArgsConstructor
public class ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;
    private final CityService cityService;

    public ChatSessionEntity findChatSession(Long chatId) {
        Optional<ChatSessionEntity> chatSessionEntityOptional = chatSessionRepository.findById(chatId);
        if (chatSessionEntityOptional.isEmpty()) {
            ChatSessionEntity chatSessionEntity = new ChatSessionEntity();
            chatSessionEntity.setChatId(chatId);
            chatSessionEntity.setStateName(START);
            chatSessionEntity.setCity(new City());
            chatSessionRepository.save(chatSessionEntity);
            return chatSessionEntity;
        } else {
            return chatSessionEntityOptional.get();
        }
    }

    public void save(ChatSessionEntity chatSession) {
        chatSessionRepository.save(chatSession);
    }

    public void overwriteStepByText(String text, ChatSessionEntity chatSessionEntity) {
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

    public void setNextStep(Long chatId, ChatStateName nextStepName) {
        ChatSessionEntity chatSessionEntity = findChatSession(chatId);
        chatSessionEntity.setStateName(nextStepName);
        save(chatSessionEntity);
    }
}
