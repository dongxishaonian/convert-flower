package cn.techflower.editor.strategy.impl;

import cn.techflower.editor.strategy.Converter;

import java.util.stream.IntStream;

import static cn.techflower.editor.utils.AlphabetUtils.convertSmall;
import static cn.techflower.editor.utils.AlphabetUtils.isBig;

public class UnderScoreCaseConverter implements Converter {
    @Override
    public String convert(String originText) {
        StringBuilder replaceText = new StringBuilder();
        char[] chars = originText.toCharArray();
        IntStream.range(0, chars.length).forEach(i -> {
            if (isBig(chars[i])) {
                replaceText.append('_');
                replaceText.append(convertSmall(chars[i]));
            } else {
                replaceText.append(chars[i]);
            }
        });

        return replaceText.toString();
    }
}
