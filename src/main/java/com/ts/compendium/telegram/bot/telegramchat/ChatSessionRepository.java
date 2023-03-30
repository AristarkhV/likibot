package com.ts.compendium.telegram.bot.telegramchat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatSessionRepository {

    private final ChatSessionMongoRepository chatSessionMongoRepository;

    public ChatSessionEntity save(ChatSessionEntity chatSessionEntity) {
        return chatSessionMongoRepository.save(chatSessionEntity);
    }

    public Optional<ChatSessionEntity> findById(Long id) {
        return chatSessionMongoRepository.findById(id);
    }
}
