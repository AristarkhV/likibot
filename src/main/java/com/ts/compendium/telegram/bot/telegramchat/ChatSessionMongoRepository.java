package com.ts.compendium.telegram.bot.telegramchat;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ChatSessionMongoRepository extends MongoRepository<ChatSessionEntity, Long> {

}
