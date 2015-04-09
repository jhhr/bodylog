package bodylog.ui.dataediting;

import bodylog.logic.Move;
import bodylog.files.ToFile;

/**
 * The UI component used in creating and editing movement data such as their
 * variables and names. Contained in a MoveEditorWindow. Implementation of the
 * abstract class Editor. Has buttons for adding and removing variables.
 *
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

    @Override
    protected void saveToFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
