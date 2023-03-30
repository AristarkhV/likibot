package com.ts.compendium.telegram.bot.telegramchat.factories;

import org.springframework.stereotype.Component;

@Component
public class OrderHistoryFactory {

    public String getOrderHistory(Long chatId) {
        return "Інформацію котру показуємо:\n" +
                "\n" +
                "- Дата: 12.03.2023\n" +
                "- Аптека АНЦ \n" +
                "- Адреса аптеки\n" +
                "- Кількість препаратів: 4\n" +
                "- Сумма замовлення: 268.78 ₴\n";
    }
}
