package bodylog.ui.dataediting;

import bodylog.logic.Move;
import javax.swing.JButton;

/**
 * Window for creating and editing Moves. Implementation of the abstract class
 * WindowWithMoveChooser. This is the container for the UI components
 * responsible for creating new Moves and editing existing ones, MoveEditors.
 *
 * @see Move
 * @see WindowWithMoveChooser
 * @see MoveEditor
 */
public class MoveEditorWindow extends WindowWithMoveChooser {

    /**
     * Creates a new window with an additional button in the top component for
     * adding new Moves.
     *
     * @throws Exception see constructor for
     * {@link bodylog.ui.dataediting.WindowWithMoveChooser#WindowWithMoveChooser() WindowWithMoveChooser}
     * @see WindowWithMoveChooser#WindowWithMoveChooser
     */
    public MoveEditorWindow() throws Exception {
        super();

        JButton newMoveButton = new JButton("new movement");
        chooserPanel.add(newMoveButton);
    }

    @Override
    protected void addEditor(Move move) {
        editorPanel.add(new MoveEditor(move, this));
        validate();
        repaint();
    }

    @Override
    protected boolean addEditorAllowed(Move move) {
        return moveHasOpenEditor(move, "editor");
    }

}
