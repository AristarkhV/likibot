package com.ts.compendium.telegram.bot.chatsession;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ChatSessionMongoRepository extends MongoRepository<ChatSessionEntity, Long> {

}
