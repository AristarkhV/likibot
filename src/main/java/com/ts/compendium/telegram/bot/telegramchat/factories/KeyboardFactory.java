package com.ts.compendium.telegram.bot.telegramchat.factories;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import com.ts.compendium.telegram.bot.enums.TelegramButton;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.ts.compendium.telegram.bot.enums.TelegramButton.*;

@Component
public class KeyboardFactory {

    public ReplyKeyboardMarkup getRequestContactKeyboard() {
        KeyboardButton shareContactButton = new KeyboardButton(SHARE_CONTACT_BUTTON.getText());
        shareContactButton.setRequestContact(true);

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(new KeyboardRow(List.of(shareContactButton))))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public ReplyKeyboardMarkup getLocationRequestKeyboard() {
        KeyboardButton button = new KeyboardButton(LOCATION_REQUEST_BUTTON.getText());
        button.setRequestLocation(true);

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(new KeyboardRow(List.of(button))))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public InlineKeyboardMarkup getChangeContactKeyboard() {
        List<TelegramButton> telegramButtons = new ArrayList<>();
        telegramButtons.add(CHANGE_CONTACT_BUTTON);
        return getInlineKeyboardMarkup(telegramButtons);
    }

    public InlineKeyboardMarkup getAuthorizedMenuKeyboard() {
        List<TelegramButton> telegramButtons = new ArrayList<>();
        telegramButtons.add(MAIN_MENU_BUTTON);
        telegramButtons.add(ORDER_DRUGS_BUTTON);
        return getInlineKeyboardMarkup(telegramButtons);
    }

    public InlineKeyboardMarkup getMainMenuKeyboard() {
        List<TelegramButton> telegramButtons = new ArrayList<>();
        telegramButtons.add(ORDER_DRUGS_BUTTON);
        telegramButtons.add(ORDER_HISTORY_BUTTON);
        return getInlineKeyboardMarkup(telegramButtons);
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup(List<TelegramButton> butttons) {
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        for (TelegramButton template : butttons) {
            InlineKeyboardButton button = getInlineKeyboardButton(template);
            inlineKeyboardButtons.add(button);
        }
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(inlineKeyboardButtons);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton getInlineKeyboardButton(TelegramButton telegramButton) {
        InlineKeyboardButton addBtn = new InlineKeyboardButton(telegramButton.getText());
        addBtn.setCallbackData(telegramButton.getCommand());
        return addBtn;
    }
}
