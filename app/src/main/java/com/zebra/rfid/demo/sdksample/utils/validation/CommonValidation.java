package com.zebra.rfid.demo.sdksample.utils.validation;

import java.util.regex.Pattern;

public class CommonValidation {
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.equals("");
    }

    public static boolean isEmailValid(String email) {
        return  EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
