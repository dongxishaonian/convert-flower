package cn.techflower.editor.enums;

import cn.techflower.editor.strategy.Converter;
import cn.techflower.editor.strategy.impl.CamelCaseConverter;
import cn.techflower.editor.strategy.impl.UnderScoreCaseConverter;
import cn.techflower.editor.utils.AlphabetUtils;

import java.util.Arrays;
import java.util.Optional;

public enum NamingStyleEnum implements Recognition {
    CAMEL_CASE(new CamelCaseConverter()) {
        @Override
        public boolean recognize(String originText) {
            return AlphabetUtils.isCamelCase(originText);
        }

        @Override
        public NamingStyleEnum nextNamingStyle() {
            return UNDER_SCORE_CASE;
        }
    },
    UNDER_SCORE_CASE(new UnderScoreCaseConverter()) {
        @Override
        public boolean recognize(String originText) {
            return AlphabetUtils.isCamelCase(originText);
        }

        @Override
        public NamingStyleEnum nextNamingStyle() {
            return CAMEL_CASE;
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
