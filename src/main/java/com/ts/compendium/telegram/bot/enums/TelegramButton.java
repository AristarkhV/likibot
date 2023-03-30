package com.ts.compendium.telegram.bot.enums;

import lombok.Getter;

@Getter
public enum TelegramButton {

    SHARE_CONTACT_BUTTON("\uD83D\uDC47 Надіслати номер телефону", ""),
    CHANGE_CONTACT_BUTTON("Змінити номер телефону", "/start"),
    MAIN_MENU_BUTTON("\uD83C\uDFE0Головне меню", "/main-menu"),
    ORDER_DRUGS_BUTTON("\uD83D\uDC8AЗамовити ліки", "/order-drugs"),
    ORDER_HISTORY_BUTTON("\uD83D\uDD04Історія замовлень", "/order-history"),
    LOCATION_REQUEST_BUTTON("👇Надіслати місцезнаходження", "/location-request");

    private final String text;
    private final String command;

    TelegramButton(String text, String command) {
        this.text = text;
        this.command = command;
    }
}
