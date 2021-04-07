package cn.techflower.editor.strategy;

import com.google.common.base.CaseFormat;

public interface Converter {
    String convert(CaseFormat currentCase, String originText);
}
