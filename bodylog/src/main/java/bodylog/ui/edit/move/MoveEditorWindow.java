package bodylog.ui.edit.move;

import bodylog.files.edit.MoveSaver;
import bodylog.ui.edit.abstracts.WindowWithMoveChooser;
import bodylog.logic.Move;
import bodylog.ui.edit.MoveChooserUpdater;
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
    
    private final MoveChooserUpdater updater;

    /**
     * Creates a new updater with an additional button in the top component for
     * adding new Moves.
     *
     * @throws Exception see constructor for
     * {@link bodylog.ui.dataediting.WindowWithMoveChooser#WindowWithMoveChooser() WindowWithMoveChooser}
     * @see bodylog.ui.dataediting.WindowWithMoveChooser#WindowWithMoveChooser
     */
    public MoveEditorWindow(MoveChooserUpdater updater) throws Exception {
        super(updater);
        this.updater = updater;

        moveChooser.add(newMoveButton());
    }

    private JButton newMoveButton() {
        JButton newMoveButton = new JButton("new movement");
        newMoveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addNewEditor(new Move());
            }
        });
        return newMoveButton;
    }

    @Override
    protected void addEditor(Move move) {
        editorPanel.add(new MoveEditor(move, new MoveSaver(move),
                this, updater));
        validate();
        repaint();
    }

    @Override
    protected boolean addEditorAllowed(Move move) {
        return moveHasOpenEditor(move, "editor");
    }

}
