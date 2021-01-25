package cn.techflower.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class CamelOrUnderScoreAction extends AnAction {

    private static final char a = 'a';
    private static final char z = 'z';
    private static final char A = 'A';
    private static final char Z = 'Z';
    private static final char UNDER_SCORE = '_';

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();

        String selectedText = primaryCaret.getSelectedText();
        if (selectedText != null && selectedText.length() < 100) {
            String targetText = handleCaseConvert(selectedText);

            WriteCommandAction.runWriteCommandAction(project, () -> document.replaceString(start, end, targetText));
        }
    }

    private String handleCaseConvert(String originText) {
        StringBuilder replaceText = new StringBuilder();
        char[] chars = originText.toCharArray();
        if (isUnderScoreCase(originText)) {
            return convertToCamelCase(replaceText, chars);
        }
        if (isCamelCase(originText)) {
            return convertToUnderScoreCase(replaceText, chars);
        }
        return originText;
    }

    private boolean isUnderScoreCase(String text) {
        return text.contains("_") && text.toLowerCase(Locale.ROOT).equals(text);
    }


    private boolean isCamelCase(String text) {
        return !text.toLowerCase(Locale.ROOT).equals(text) && !text.contains("_");
    }

    private String convertToUnderScoreCase(StringBuilder replaceText, char[] chars) {
        replaceText.append(chars[0]);
        IntStream.range(1, chars.length).forEach(i -> {
            if (isSmall(chars[i])) {
                replaceText.append(chars[i]);
            } else if (isBig(chars[i])) {
                replaceText.append('_');
                replaceText.append(convertSmall(chars[i]));
            }
        });

        return replaceText.toString();
    }

    private String convertToCamelCase(StringBuilder replaceText, char[] chars) {
        replaceText.append(chars[0]);

        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == UNDER_SCORE) {
                if (isSmall(chars[i + 1])) {
                    replaceText.append(convertBig(chars[i + 1]));
                    i++;
                }
            } else {
                replaceText.append(chars[i]);
            }
        }

        return replaceText.toString();
    }

    private boolean isSmall(char aChar) {
        return aChar >= a && aChar <= z;
    }

    private boolean isBig(char aChar) {
        return aChar >= A && aChar <= Z;
    }

    private char convertBig(char aChar) {
        return (char) (aChar - 32);
    }

    private char convertSmall(char aChar) {
        return (char) (aChar + 32);
    }

    @Override
    public void update(@NotNull final AnActionEvent e) {
        // Get required data keys
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        // Set visibility and enable only in case of existing project and editor and if a selection exists
        e.getPresentation().setEnabledAndVisible(
                project != null && editor != null && editor.getSelectionModel().hasSelection()
        );
    }

}
