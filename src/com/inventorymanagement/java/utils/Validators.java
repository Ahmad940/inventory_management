/*
 *  Created by @Mak
 *  User: Ahmad
 *  Date: 8/9/2020
 *  Time: 7:21 PM
 */
package com.inventorymanagement.java.utils;

import java.util.regex.Pattern;

public class Validators {
    public static Boolean isValidEmail(String value) {
        boolean pattern = Pattern.matches("\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}", value);
        if (pattern)
            return true;

        return false;
    }

    public static Boolean isDouble(String value) {
        char[] letters = value.toCharArray();

        // check if empty
        if (letters.length < 1)
            return false;

        for (char letter : letters) {
            if (Double.isNaN(letter)) {
                return true;
            }
//            if (!Character.isDigit(letter)) {
//                return false;
//            }
        }
        return true;
    }

    public static Boolean isNumber(String value) {
        char[] letters = value.toCharArray();

        // check if empty
        if (letters.length < 1)
            return false;

        for (char letter : letters) {
            if (!Character.isDigit(letter)) {
                return false;
            }
        }
        return true;
    }
}