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

        if (input.length() == 1) {
            return input;
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isDigit(c)) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(c);
            }
        }

        return result.toString();
    }

    public static String joinListWithNewLine(List<String> strings) {
        return String.join("\\n", strings);
    }

    public static String replaceNewlineWithLiteral(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("\n", "\\n");
    }
}
