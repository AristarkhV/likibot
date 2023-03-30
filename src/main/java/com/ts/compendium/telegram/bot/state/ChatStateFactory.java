/*
 * *************************************************************************
 * * Yaypay CONFIDENTIAL   2018
 * * All Rights Reserved. * *
 * NOTICE: All information contained herein is, and remains the property of Yaypay Incorporated and its suppliers, if any.
 * The intellectual and technical concepts contained  herein are proprietary to Yaypay Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material  is strictly forbidden unless prior written permission is obtained  from Yaypay Incorporated.
 */

package com.ts.compendium.telegram.bot.state;

import org.springframework.stereotype.Component;
import com.ts.compendium.telegram.bot.enums.ChatStateName;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static com.ts.compendium.telegram.bot.enums.ChatStateName.*;

@Component
public class ChatStateFactory {

    private final Map<ChatStateName, ChatState> chatStates;

    public ChatStateFactory(List<ChatState> chatStates) {
        this.chatStates = chatStates.stream().collect(toMap(ChatState::getChatStateName, chatState -> chatState));
    }

    public ChatState getStateByName(ChatStateName stateName) {
        return chatStates.get(stateName);
    }
}
