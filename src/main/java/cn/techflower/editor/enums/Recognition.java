package cn.techflower.editor.enums;

import com.google.common.base.CaseFormat;

public interface Recognition {
    boolean recognize(String originText);

    NamingStyleEnum nextNamingStyle();

    CaseFormat getCaseFormat();
}
