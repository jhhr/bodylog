package bodylog.ui.edit.move;

import bodylog.files.write.MoveSaver;
import bodylog.ui.edit.WindowWithMoveChooser;
import bodylog.logic.Move;
import bodylog.ui.MoveListContainerUpdater;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Window for creating and editing Moves. Implementation of the abstract class
 * WindowWithMoveChooser. This is the container for the UI components
 * responsible for creating new Moves and editing existing ones, MoveEditors.
 *
 * @see bodylog.logic.Move
 * @see bodylog.ui.dataediting.WindowWithMoveChooser
 * @see bodylog.ui.edit.move.MoveEditor
 */
public class MoveEditorWindow extends WindowWithMoveChooser {

    /**
     * Creates a new window with an additional button in the top component for
     * adding new Moves.
     *
     * @param updater MoveListContainerUpdater given by MainWindow
     * @throws Exception see constructor for
     * {@link bodylog.ui.dataediting.WindowWithMoveChooser#WindowWithMoveChooser() WindowWithMoveChooser}
     * @see bodylog.ui.dataediting.WindowWithMoveChooser#WindowWithMoveChooser
     */
    public MoveEditorWindow(MoveListContainerUpdater updater) throws Exception {
        super(updater);

        moveListContainer.add(newMoveButton());
    }

    private JButton newMoveButton() {
        JButton newMoveButton = new JButton("new movement");
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
