package cn.techflower.editor.enums;

public interface Recognition {
    boolean recognize(String originText);

    NamingStyleEnum nextNamingStyle();
}
