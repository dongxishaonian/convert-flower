package cn.techflower.editor.strategy.impl;

import cn.techflower.editor.strategy.Converter;
import cn.techflower.editor.utils.AlphabetUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SmallCamelCaseConverter implements Converter {

    @Override
    public String convert(List<String> originTextList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < originTextList.size(); i++) {
            char[] chars = originTextList.get(i).toCharArray();
            if (i == 0) {
                chars[0] = AlphabetUtils.convertSmall(chars[0]);
            } else {
                chars[0] = AlphabetUtils.convertBig(chars[0]);
            }
            sb.append(chars);
        }
        return String.valueOf(sb);
    }
}
