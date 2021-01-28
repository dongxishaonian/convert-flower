package cn.techflower.editor.utils;

import cn.techflower.editor.constants.EditorConstants;

import java.util.Locale;

public class AlphabetUtils {
    public static boolean isSmall(char aChar) {
        return aChar >= EditorConstants.SMALL_A && aChar <= EditorConstants.SMALL_Z;
    }

    public static boolean isBig(char aChar) {
        return aChar >= EditorConstants.BIG_A && aChar <= EditorConstants.BIG_Z;
    }

    public static char convertBig(char aChar) {
        return (char) (aChar - 32);
    }

    public static char convertSmall(char aChar) {
        return (char) (aChar + 32);
    }

    public static boolean isUnderScoreCase(String text) {
        return text.contains("_") && text.toLowerCase(Locale.ROOT).equals(text);
    }


    public static boolean isCamelCase(String text) {
        return !text.toLowerCase(Locale.ROOT).equals(text) && !text.contains("_");
    }
}
