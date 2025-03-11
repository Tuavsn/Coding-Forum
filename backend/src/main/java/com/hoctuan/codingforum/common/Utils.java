package com.hoctuan.codingforum.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    /**
     * Split string by semicolon
     * 
     * @param input
     * @return
     */
    public static List<String> splitStringBySemicolon(String input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }
        return List.of(input.split("\\s*;\\s*"));
    }

    /**
     * Split string by comma
     * 
     * @param input
     * @return
     */
    public static List<String> splitStringByComma(String input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }
        return List.of(input.split("\\s*,\\s*"));
    }

    /**
     * Split string by pipe
     * 
     * @param input
     * @return
     */
    public static List<String> splitStringByPipe(String input) {
        if (input == null || input.isEmpty()) {
            return List.of();
        }
        return List.of(input.split("\\s*\\|\\s*"));
    }

    /**
     * Split string by space
     * 
     * @param input
     * @return
     */
    public static List<String> splitStringBySpace(List<String> input) {
        List<String> result = new ArrayList<>();
        for (String item : input) {
            if (item.contains(" ")) {
                String[] splitItems = item.split("\\s*\\+\\s*");
                result.addAll(Arrays.asList(splitItems));
            } else {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Join string with new line
     * 
     * @param strings
     * @return
     */
    public static String joinListWithNewLine(List<String> strings) {
        return String.join("\n", strings);
    }
}
