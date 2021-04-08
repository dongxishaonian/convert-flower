package cn.techflower.editor.utils;

import java.util.Locale;

public class AlphabetUtils {

    public static boolean isUnderScoreCase(String text) {
        return text.contains("_") && text.toLowerCase(Locale.ROOT).equals(text);
    }

    public static boolean isHyphenCase(String text) {
        return text.contains("-") && text.toLowerCase(Locale.ROOT).equals(text);
    }


    public static boolean isSmallCamelCase(String text) {
        return text.matches("[a-z]+((\\d)|([A-Z0-9][a-z0-9]+))*([A-Z])?");
    }

    public static boolean isBigCamelCase(String text) {
        return text.matches("([A-Z][a-z0-9]+)((\\d)|([A-Z0-9][a-z0-9]+))*([A-Z])?");
    }
}
