package cn.techflower.editor.strategy.impl;

import cn.techflower.editor.strategy.Converter;

import static cn.techflower.editor.constants.EditorConstants.UNDER_SCORE;
import static cn.techflower.editor.utils.AlphabetUtils.convertBig;
import static cn.techflower.editor.utils.AlphabetUtils.isSmall;

public class CamelCaseConverter implements Converter {
    @Override
    public String convert(String originText) {
        StringBuilder replaceText = new StringBuilder();
        char[] chars = originText.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == UNDER_SCORE) {
                if (isSmall(chars[i + 1])) {
                    replaceText.append(convertBig(chars[i + 1]));
                    i++;
                }
            } else {
                replaceText.append(chars[i]);
            }
        }

        return replaceText.toString();
    }
}
