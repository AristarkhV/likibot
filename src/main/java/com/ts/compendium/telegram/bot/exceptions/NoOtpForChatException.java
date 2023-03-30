package com.ts.compendium.telegram.bot.exceptions;

import java.util.NoSuchElementException;

public class NoOtpForChatException extends NoSuchElementException {

    public NoOtpForChatException(String message) {
        super(message);
    }
}
