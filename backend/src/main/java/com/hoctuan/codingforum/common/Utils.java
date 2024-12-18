package com.hoctuan.codingforum.common;

import java.util.List;

public class Utils {

    public static List<String> splitStringBySemicolon(String input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }

        return List.of(input.split("\\s*;\\s*"));
    }

    public static List<String> splitStringByComma(String input) {
        if(input == null || input.isEmpty()) {
            return List.of();
        }

        return List.of(input.split("\\s*,\\s*"));
    }

    public static List<String> splitStringByPipe(String input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }

        return List.of(input.split("\\s*\\|\\s*"));
    }

    public static String separateDigitsWithSpace(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
    
        StringBuilder result = new StringBuilder();
        boolean foundDigit = false;
    
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
    
            if (c == '-' && !foundDigit && result.length() == 0) {
                result.append(c);
                continue;
            }
    
            // Process digits
            if (Character.isDigit(c)) {
                if (foundDigit) {
                    result.append(" ");
                }
                result.append(c);
                foundDigit = true;
            }
        }
    
        return result.toString();
    }
    

    public static String joinListWithNewLine(List<String> strings) {
        return String.join("\n", strings);
    }
}
