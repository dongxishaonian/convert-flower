package cn.techflower.editor.enums;

import cn.techflower.editor.strategy.Converter;
import cn.techflower.editor.strategy.impl.BigCamelCaseConverter;
import cn.techflower.editor.strategy.impl.UnderScoreCaseConverter;
import cn.techflower.editor.utils.AlphabetUtils;
import com.google.common.base.CaseFormat;

import java.util.Arrays;
import java.util.Optional;

public enum NamingStyleEnum implements Recognition {
    BIG_CAMEL_CASE(new BigCamelCaseConverter()) {
        @Override
        public boolean recognize(String originText) {
            return AlphabetUtils.isBigCamelCase(originText);
        }

        @Override
        public NamingStyleEnum nextNamingStyle() {
            return UNDER_SCORE_CASE;
        }

        @Override
        public CaseFormat getCaseFormat() {
            return CaseFormat.UPPER_CAMEL;
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
        public CaseFormat getCaseFormat() {
            return CaseFormat.LOWER_UNDERSCORE;
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
