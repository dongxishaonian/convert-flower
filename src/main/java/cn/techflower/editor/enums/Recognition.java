package cn.techflower.editor.enums;

import java.util.List;

public interface Recognition {
    boolean recognize(String originText);

    NamingStyleEnum nextNamingStyle();

    List<String> split(String origin);
}
