package bodylog.ui.edit.move;

import bodylog.files.write.MoveSaver;
import bodylog.ui.edit.WindowWithMoveChooser;
import bodylog.logic.Move;
import bodylog.logic.exceptions.ParsingException;
import bodylog.logic.exceptions.VariableStateException;
import bodylog.ui.MoveListContainerUpdater;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JButton;

/**
 * Window for creating and editing Moves. Implementation of the abstract class
 * WindowWithMoveChooser. This is the container for the UI components
 * responsible for creating new Moves and editing existing ones, MoveEditors.
 *
 * @see bodylog.logic.Move
 * @see bodylog.ui.edit.WindowWithMoveChooser
 * @see bodylog.ui.edit.move.MoveEditor
 */
public class MoveEditorWindow extends WindowWithMoveChooser {

    private static final String NEW_MOVE_TITLE = "New Movement";

    /**
     * Creates a new window with an additional button in the top component for
     * adding new Moves.
     *
     * @param updater the MoveListContainerUpdater given by MainWindow
     *
     * @throws SecurityException if a file cannot be accessed
     * @throws FileNotFoundException if a file is not found while creating the
     * moveList for the updater
     * @throws ParsingException if parsing the Moves from file fails
     * @throws VariableStateException if a variable parsed from file is found to
     * be improper
     *
     * @see bodylog.ui.edit.WindowWithMoveChooser#WindowWithMoveChooser
     */
    public MoveEditorWindow(MoveListContainerUpdater updater)
            throws SecurityException, ParsingException, VariableStateException,
            FileNotFoundException {
        super(updater);

        moveListContainer.add(newMoveButton());
    }

    private JButton newMoveButton() {
        JButton newMoveButton = new JButton(NEW_MOVE_TITLE);
        newMoveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedAction(new Move());
            }
        });
        return newMoveButton;
    }

    @Override
    protected void addEditor(Move move) {
        editorPanel.add(new MoveEditor(new MoveSaver(updater, move),
                this));
        validate();
        repaint();
    }

    @Override
    protected boolean addEditorAllowed(Move move) {
        return moveHasOpenEditor(move, "editor");
    }

}
