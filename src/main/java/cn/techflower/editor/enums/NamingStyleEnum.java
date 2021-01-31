package cn.techflower.editor.enums;

import cn.techflower.editor.strategy.Converter;
import cn.techflower.editor.strategy.impl.BigCamelCaseConverter;
import cn.techflower.editor.strategy.impl.UnderScoreCaseConverter;
import cn.techflower.editor.utils.AlphabetUtils;

import java.util.*;
import java.util.stream.Collectors;

public enum NamingStyleEnum implements Recognition {
    BIG_CAMEL_CASE(new BigCamelCaseConverter()) {
        @Override
        public boolean recognize(String originText) {
            return AlphabetUtils.isCamelCase(originText);
        }

        @Override
        public NamingStyleEnum nextNamingStyle() {
            return UNDER_SCORE_CASE;
        }

        @Override
        public List<String> split(String origin) {
            List<String> splitStringList = new ArrayList<>();
            int start = 0;
            for (int i = 0; i < origin.length(); i++) {
                if (AlphabetUtils.isBig(origin.charAt(i))) {
                    splitStringList.add(origin.substring(start, i).toLowerCase(Locale.ROOT));
                    start = i;
                }
            }
            splitStringList.add(origin.substring(start).toLowerCase(Locale.ROOT));
            return splitStringList;
        }
    },
    UNDER_SCORE_CASE(new UnderScoreCaseConverter()) {
        @Override
        public boolean recognize(String originText) {
            return AlphabetUtils.isUnderScoreCase(originText);
        }

        @Override
        public NamingStyleEnum nextNamingStyle() {
            return BIG_CAMEL_CASE;
        }

        @Override
        public List<String> split(String origin) {
            return Arrays.stream(origin.split("_")).collect(Collectors.toList());
        }
    };

    private final Converter converter;

    NamingStyleEnum(Converter converter) {
        this.converter = converter;
    }

    public Converter getConverter() {
        return converter;
    }

    public static Optional<NamingStyleEnum> getNamingStyleEnum(String text) {
        return Arrays.stream(NamingStyleEnum.values()).filter(s -> s.recognize(text)).findFirst();
    }
}
