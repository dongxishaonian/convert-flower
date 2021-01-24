// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.intellij.sdk.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Menu action to replace a selection of characters with a fixed string.
 *
 * @see AnAction
 */
public class EditorIllustrationAction extends AnAction {

    /**
     * Replaces the run of text selected by the primary caret with a fixed string.
     *
     * @param e Event related to this action
     */
    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        // Get all the required data from data keys
        // Editor and Project were verified in update(), so they are not null.
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();
        // Work off of the primary caret to get the selection info
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();

        String selectedText = primaryCaret.getSelectedText();
        if (selectedText != null && selectedText.length() < 100) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] chars = selectedText.toCharArray();
            IntStream.range(1, chars.length).forEach(i -> {
                if (chars[i] >= 'a' && chars[i] <= 'z') {
                    stringBuilder.append(chars[i]);
                } else if (chars[i] >= 'A' && chars[i] <= 'Z') {
                    stringBuilder.append('_');
                    stringBuilder.append((char)(chars[i] + 32));
                }
            });

            WriteCommandAction.runWriteCommandAction(project, () ->
                    document.replaceString(start, end, stringBuilder.toString())
            );
        }
        // Replace the selection with a fixed string.
        // Must do this document change in a write action context.
        // De-select the text range that was just replaced
        primaryCaret.removeSelection();
    }

    /**
     * Sets visibility and enables this action menu item if:
     * <ul>
     *   <li>a project is open</li>
     *   <li>an editor is active</li>
     *   <li>some characters are selected</li>
     * </ul>
     *
     * @param e Event related to this action
     */
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
