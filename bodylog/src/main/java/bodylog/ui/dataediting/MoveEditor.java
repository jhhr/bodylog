package bodylog.ui.dataediting;

import bodylog.logic.Move;
import bodylog.files.ToFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The UI component used in creating new movements and editing existing ones.
 * Contained in a MoveEditorWindow. Implementation of the abstract class Editor.
 * Has buttons for adding and removing variables.
 *
 * @see Move
 * @see MoveEditorWindow
 * @see Editor
 */
public class MoveEditor extends Editor {

    public MoveEditor(Move move, MoveEditorWindow window) {
        super(move, window);

        add(closeButton("close movement"));
        setEditorBorder("");
    }

    private void createMove() {

    }

    /**
     * Checks whether a move file with the same name as the Move of this Editor
     * already exists using <code>ToFile.moveFileExists</code>.
     *
     * @return true if the file exists, false otherwise
     * @see ToFile#moveFileExists
     */
    @Override
    protected boolean fileExists() {
        return ToFile.moveFileExists(move);
    }

    /**
     * Writes the edited Move into a move fine using the method
     * <code>ToFile.move</code>.
     *
     * @see ToFile#move
     */
    @Override
    protected void saveToFile() {
        try {
            ToFile.move(move);
        } catch (Exception ex) {
            Logger.getLogger(MoveEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
