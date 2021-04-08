package cn.techflower.editor;

import cn.techflower.editor.enums.NamingStyleEnum;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NamingStyleAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();

        SelectionModel selectionModel = editor.getSelectionModel();

        int start = selectionModel.getSelectionStart();
        int end = selectionModel.getSelectionEnd();

        String selectedText = selectionModel.getSelectedText();
        if (selectedText != null && selectedText.length() < 100) {
            String targetText = handleCaseConvert(selectedText);

            WriteCommandAction.runWriteCommandAction(project, () -> document.replaceString(start, end, targetText));
        }
    }

    private String handleCaseConvert(String originText) {
        Optional<NamingStyleEnum> namingStyleEnumOptional = NamingStyleEnum.getNamingStyleEnum(originText);
        if (namingStyleEnumOptional.isPresent()) {
            NamingStyleEnum namingStyleEnum = namingStyleEnumOptional.get();
            return namingStyleEnum.nextNamingStyle().getConverter().convert(namingStyleEnum.getCaseFormat(), originText);
        }

        return originText;
    }

    @Override
    public void update(@NotNull final AnActionEvent e) {
        // Get required data keys
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        // Set visibility and enable only in case of existing project and editor and if a selection exists
        e.getPresentation().setEnabledAndVisible(
                project != null && editor != null
        );
    }

}
