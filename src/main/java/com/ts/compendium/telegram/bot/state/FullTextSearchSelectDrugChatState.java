package com.ts.compendium.telegram.bot.state;//package com.ts.compendium.telegram.bot.state;
//
//import com.ts.compendium.api.ui.drug.dto.DrugFastSearchResponse;
//import com.ts.compendium.api.ui.drug.dto.DrugSearchDTO;
//import com.ts.compendium.telegram.bot.LikieTelegramBot;
//import com.ts.compendium.usecases.DrugFastSearchUseCase;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import com.ts.compendium.telegram.bot.enums.StateName;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.google.common.collect.Lists.newArrayList;
//import static com.ts.compendium.repository.primary.drug.Locale.UK;
//import static com.ts.compendium.telegram.bot.enums.StateName.SEARCH_RESULT;
//import static com.ts.compendium.telegram.bot.enums.StateName.START;
//
//@Component
//@RequiredArgsConstructor
//public class FullTextSearchSelectDrugChatState implements ChatState {
//
//    private final DrugFastSearchUseCase drugFastSearchUseCase;
//
//    @Override
//    public StateName getChatStateName() {
//        return SEARCH_RESULT;
//    }
//
//    @Override
//    public void preProcess(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
//        likieTelegramBot.sendMessage(chatId, "Напишіть які ліких ви хотіли би придбати!");
//    }
//
//    @Override
//    public String processRequest(Long chatId, String text, LikieTelegramBot likieTelegramBot) throws TelegramApiException {
//        DrugFastSearchResponse fastSearchResponse = drugFastSearchUseCase.fastSearch(text, null, "Дніпро", UK);
//        for (DrugSearchDTO drug : fastSearchResponse.getDrugs()) {
//            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//            inlineKeyboardButton.setCallbackData(drug.getId());
//            inlineKeyboardButton.setText(drug.getName());
//            List<InlineKeyboardButton> inlineKeyboardButtons = newArrayList(inlineKeyboardButton);
//            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
//            keyboard.add(inlineKeyboardButtons);
//            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//            inlineKeyboardMarkup.setKeyboard(keyboard);
//            likieTelegramBot.sendMessage(
//                    chatId,
//                    "https://spaces.likie.ua/drug/image/M/" + drug.getId() + ".webp",
//                    inlineKeyboardMarkup);
//        }
//        return START;
//    }
//}
