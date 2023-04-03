package com.ts.compendium.telegram.bot.chatsession;

import com.ts.compendium.telegram.bot.city.City;
import com.ts.compendium.telegram.bot.enums.ChatStateName;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ChatSessionEntity {

    @Id
    private Long chatId;

    private ChatStateName stateName;

    private City city;
}
