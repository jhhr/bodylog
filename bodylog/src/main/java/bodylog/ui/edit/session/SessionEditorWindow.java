package bodylog.ui.edit.session;

import bodylog.files.write.SessionSaver;
import bodylog.ui.edit.WindowWithMoveChooser;
import bodylog.logic.Move;
import bodylog.ui.MoveListContainerUpdater;
import javax.swing.JOptionPane;

/**
 * Window for adding session data. Implementation of the abstract class
 * WindowWithMoveChooser. This is the container for the UI components
 * responsible for creating session data, SessionEditors.
 *
 * @see bodylog.ui.edit.WindowWithMoveChooser
 * @see bodylog.ui.edit.session.SessionEditor
 */
public class SessionEditorWindow extends WindowWithMoveChooser {

    public SessionEditorWindow(MoveListContainerUpdater updater) throws Exception {
        super(updater);
    }

    @Override
    protected void addEditor(Move move) {
        editorPanel.add(new SessionEditor(new SessionSaver(updater, move), this));
        validate();
        repaint();
    }

    /**
     * Checks if the given move has no variables or if there's an existing
     * SessionEditor for the move. If so for either case, adding an editor for
     * the Move is not allowed. Used in <code>moveSelectedAction</code>.
     *
     * @param move Move used in checking if adding an Editor is allowed
     *
     * @return true if allowed, false otherwise
     *
     * @see bodylog.ui.edit.session.SessionEditorWindow#addEditorAllowed
     */
    @Override
    protected boolean addEditorAllowed(Move move) {
        return (moveHasNoVariables(move) || moveHasOpenEditor(move, "session adder"));
    }

    private boolean moveHasNoVariables(Move liike) {
        if (liike.getVariableCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "The movement has no variables, add some",
                    "Not possible", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
}
