package md.cernev.plugins;

import com.intellij.mock.Mock;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.compiler.Validator;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.NonEmptyInputValidator;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;

public class CopyClass extends EditorAction {

    protected CopyClass(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }

    public CopyClass() {
        this(new UpHandler());
    }

    private static class UpHandler extends EditorWriteActionHandler {
        public UpHandler() {
        }

        @Override
        public void executeWriteAction(Editor editor, DataContext dataContext) {
            Document document = editor.getDocument();
            if (editor == null || document == null || !document.isWritable()) {
                return;
            }
            String countS = Messages.showInputDialog(editor.getProject(), "Please enter the count of paste lines:", "Multipaste", Messages.getInformationIcon(), "0", new NonEmptyInputValidator());
            int count=0;
            try{
                assert countS != null;
                 count = Integer.parseInt(countS);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Messages.showErrorDialog("Please enter a numeric value!","ERRROR PARSING");
            }

            CaretModel caretModel = editor.getCaretModel();
            SelectionModel selectionModel = editor.getSelectionModel();
            TextRange charsRange = new TextRange(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd());
            TextRange linesRange = new TextRange(document.getLineNumber(charsRange.getStartOffset()), document.getLineNumber(charsRange.getEndOffset()));
            TextRange linesBlock = new TextRange(document.getLineStartOffset(linesRange.getStartOffset()), document.getLineEndOffset(linesRange.getEndOffset()));

            String duplicate = document.getText().substring(linesBlock.getStartOffset(), linesBlock.getEndOffset());
            duplicate += "\n";
            for (int i = 0; i < count-1; i++) {

                document.insertString(linesBlock.getStartOffset(), duplicate);
            }
            editor.getSelectionModel().setSelection(linesBlock.getStartOffset(), linesBlock.getEndOffset());
            caretModel.moveToOffset(linesBlock.getEndOffset());
            editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
        }
    }
}
