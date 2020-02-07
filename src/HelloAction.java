import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class HelloAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String inputDialog = Messages.showInputDialog(e.getProject(), "Enter the count of copies: ", "Multi-Paste", Messages.getQuestionIcon());
        Messages.showMessageDialog(e.getProject(), "You have typed: "+inputDialog, "Demo", Messages.getInformationIcon());
    }

}
