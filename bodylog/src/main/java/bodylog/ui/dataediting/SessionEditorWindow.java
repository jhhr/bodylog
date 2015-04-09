package bodylog.ui.dataediting;

import bodylog.logic.Move;
import javax.swing.JOptionPane;

/**
 * Window for adding session data. Implementation of the abstract class
 * WindowWithMoveChooser. This is the container for the UI components
 * responsible for creating session data, SessionEditors.
 *
 * @see WindowWithMoveChooser
 * @see SessionEditor
 */
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

    /**
     * Checks if the given move has no variables or if there's an existing
     * SessionEditor for the move. If so for either case, the update is not
     * allowed. Used in <code>updateWindow</code>.
     *
     * @param move Move used in checking if the update is allowed
     * @return true if allowed, false otherwise
     * @see SessionEditorWindow#updateAllowed
     */
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
