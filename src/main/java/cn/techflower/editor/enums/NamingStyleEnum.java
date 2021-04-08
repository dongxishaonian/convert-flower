package cn.techflower.editor.enums;

import cn.techflower.editor.strategy.Converter;
import cn.techflower.editor.strategy.impl.BigCamelCaseConverter;
import cn.techflower.editor.strategy.impl.HyphenCaseConverter;
import cn.techflower.editor.strategy.impl.SmallCamelCaseConverter;
import cn.techflower.editor.strategy.impl.UnderScoreCaseConverter;
import cn.techflower.editor.utils.AlphabetUtils;
import com.google.common.base.CaseFormat;

import java.util.Arrays;
import java.util.Optional;

public enum NamingStyleEnum implements Recognition {
    BIG_CAMEL_CASE(new BigCamelCaseConverter(), CaseFormat.UPPER_CAMEL) {
        @Override
        public boolean recognize(String originText) {
            return AlphabetUtils.isBigCamelCase(originText);
        }

        @Override
        public NamingStyleEnum nextNamingStyle() {
            return SMALL_CAMEL_CASE;
        }
    },

    SMALL_CAMEL_CASE(new SmallCamelCaseConverter(), CaseFormat.LOWER_CAMEL) {
        @Override
        public boolean recognize(String originText) {
            return AlphabetUtils.isSmallCamelCase(originText);
        }

        @Override
        public NamingStyleEnum nextNamingStyle() {
            return UNDER_SCORE_CASE;
        }
    },

    UNDER_SCORE_CASE(new UnderScoreCaseConverter(), CaseFormat.LOWER_UNDERSCORE) {
        @Override
        public boolean recognize(String originText) {
            return AlphabetUtils.isUnderScoreCase(originText);
        }

        @Override
        public NamingStyleEnum nextNamingStyle() {
            return HYPHEN_CASE;
        }
    },

    HYPHEN_CASE(new HyphenCaseConverter(), CaseFormat.LOWER_HYPHEN) {
        @Override
        public boolean recognize(String originText) {
            return AlphabetUtils.isHyphenCase(originText);
        }

        @Override
        public NamingStyleEnum nextNamingStyle() {
            return BIG_CAMEL_CASE;
        }
    };

    private final Converter converter;

    private final CaseFormat caseFormat;

    NamingStyleEnum(Converter converter, CaseFormat caseFormat) {
        this.converter = converter;
        this.caseFormat = caseFormat;
    }

    public Converter getConverter() {
        return converter;
    }

    public CaseFormat getCaseFormat() {
        return caseFormat;
    }

    public static Optional<NamingStyleEnum> getNamingStyleEnum(String text) {
        return Arrays.stream(NamingStyleEnum.values()).filter(s -> s.recognize(text)).findFirst();
    }
}
