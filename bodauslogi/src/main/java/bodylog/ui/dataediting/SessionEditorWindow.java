package bodylog.ui.dataediting;

import bodylog.logic.Move;
import javax.swing.JOptionPane;

public class SessionEditorWindow extends WindowWithMoveChooser {

    public SessionEditorWindow() throws Exception {
        super();
    }

    @Override
    protected void addEditor(Move move) {
        editorPanel.add(new SessionEditor(move, this));
        validate();
        repaint();
    }

    @Override
    protected boolean updateAllowed(Move move) {
        return (moveHasNoVariables(move) || moveHasOpenEditor(move, "session adder"));
    }

    private boolean moveHasNoVariables(Move liike) {
        if (liike.variableCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "The movement has no variables, add some",
                    "Not allowed", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
}
