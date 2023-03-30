package com.ts.compendium.telegram.bot.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobilePhoneNumberValidatorUtils {

    public static boolean isValidMobileNo(String str) {
        if (str.startsWith("+")) {
            str = str.replace("+", "");
        }
        if (str.startsWith("38")) {
            str = str.substring(2);
        }
        Pattern ptrn = Pattern.compile("\\d+");
        Matcher match = ptrn.matcher(str);
        return (str.length() == 10
                && match.find() && match.group().equals(str));
    }
}