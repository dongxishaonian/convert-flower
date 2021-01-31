package cn.techflower.editor.strategy.impl;

import cn.techflower.editor.strategy.Converter;
import cn.techflower.editor.utils.AlphabetUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BigCamelCaseConverter implements Converter {

    @Override
    public String convert(List<String> originTextList) {
        return originTextList.stream().map(t -> {
            char[] chars = t.toCharArray();
            chars[0] = AlphabetUtils.convertBig(chars[0]);
            return String.valueOf(chars);
        }).collect(Collectors.joining());
    }
}
