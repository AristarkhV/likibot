package com.ts.compendium.telegram.bot.telegramchat.factories;

import org.springframework.stereotype.Component;

@Component
public class OrderHistoryFactory {

    public String getOrderHistory(Long chatId) {
        return """
                Інформацію котру показуємо:

                - Дата: 12.03.2023
                - Аптека АНЦ\s
                - Адреса аптеки
                - Кількість препаратів: 4
                - Сумма замовлення: 268.78 ₴
                """;
    }
}
