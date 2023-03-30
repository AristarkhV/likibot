package com.ts.compendium.telegram.bot.telegramchat.factories;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class MessageFactory {
    private static final String PREFIX = "src/main/resources/telegram/dialogs/";

    public String get(String messageType) {
        return switch (messageType) {
            case "START" -> readFile("start.html");
            case "DEFAULT" -> readFile("default.html");
            case "PHONE_NUMBER_REQUEST" -> readFile("number-request.html");
            case "INVALID_NUMBER" -> readFile("invalid-phone-number.html");
            case "SMS_SENT" -> readFile("sms-sent.html");
            case "AUTHORIZED" -> readFile("authorized.html");
            case "UNKNOWN_MESSAGE" -> readFile("unknown-message.html");
            case "MAIN_MENU" -> readFile("main-menu.html");
            case "LOCATION_REQUEST" -> readFile("location-request.html");
            case "HELP" -> readFile("help.txt");
            case "SEARCH" -> readFile("search.txt");
            default -> throw new RuntimeException("UnknownCommand");
        };
    }

    public String get(String messageType, String inputData) {
        return switch (messageType) {
            case "SMS_SENT" -> readFile("sms-sent.html").replace("{userPhoneNumber}", inputData);
            case "INVALID_OTP" -> readFile("invalid-otp-code.html").replace("{userPhoneNumber}", inputData);
            default -> throw new RuntimeException("UnknownCommand");
        };
    }

    private String readFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(PREFIX + fileName)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file for chat + " + PREFIX + fileName, e);
        }
    }
}
