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
        if (isSmall(aChar)) {
            return (char) (aChar - 32);
        }
        return aChar;
    }

    public static char convertSmall(char aChar) {
        if (isBig(aChar)) {
            return (char) (aChar + 32);
        }
        return aChar;
    }

    public static boolean isUnderScoreCase(String text) {
        return text.contains("_") && text.toLowerCase(Locale.ROOT).equals(text);
    }


    public static boolean isSmallCamelCase(String text) {
        return text.matches("[a-z]+((\\d)|([A-Z0-9][a-z0-9]+))*([A-Z])?");
    }

    public static boolean isBigCamelCase(String text){
        return  text.matches("([A-Z][a-z0-9]+)((\\d)|([A-Z0-9][a-z0-9]+))*([A-Z])?");
    }
}
