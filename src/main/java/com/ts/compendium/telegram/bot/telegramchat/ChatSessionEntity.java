package com.ts.compendium.telegram.bot.telegramchat;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.ts.compendium.telegram.bot.enums.ChatStateName;

@Data
@Document
public class ChatSessionEntity {

    @Id
    private Long chatId;

    private ChatStateName stateName;
}
