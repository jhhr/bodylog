package bodylog.ui.edit.move;

import bodylog.files.Saver;
import bodylog.ui.edit.Editor;
import bodylog.logic.Move;
import bodylog.logic.DataHandling;
import bodylog.ui.MoveListContainerUpdater;
import bodylog.ui.tables.edit.MoveEditorTable;
import bodylog.ui.tables.abstracts.EditorTable;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 * The UI component used in creating new movements and editing existing ones.
 * Contained in a MoveEditorWindow. Implementation of the abstract class Editor.
 * Variable editing is done in a JTable using EditorTable as its TableModel. Has
 * buttons for adding and removing variables and a field for setting the name.
 *
 * @see bodylog.logic.Move
 * @see bodylog.ui.edit.move.MoveEditorWindow
 * @see bodylog.ui.dataediting.Editor
 */
public class MoveEditor extends Editor {

    private final MoveListContainerUpdater updater;

    /**
     * Creates a new <code>MoveEditor</code> for the given <code>Move</code>
     * which can be a newly created blank one. Identifies whether the
     * <code>Move</code> being edited is a new one or loaded from a move move by
     * checking in the constructor if a move move corresponding to this Move
     * exists. If so, the Move must've been loaded from that. This affects
     * behavior on saving the Move to move.
     *
     * @param move Move to be edited
     * @param saver
     * @param window Container of this <code>MoveEditor</code>
     * @param updater MoveListContainerUpdater used to update list contents in all
 existing MoveChoosers
     */
    public MoveEditor(Move move, Saver saver, MoveEditorWindow window,
            MoveListContainerUpdater updater) {
        super(move, saver, window);
        this.updater = updater;

        setEditorBorder("");

        setButtonLayouts("add variable", "remove variable",
                "name:", "Characters not allowed in name: "
                + DataHandling.IllegalCharsWithSpaces(DataHandling.Illegal.MOVE_NAME),
                "", "close movement");
    }

    @Override
    protected EditorTable setTableModel() {
        Object[][] tableData;
        int varCount = move.variableCount();
        if (varCount > 0) {
            tableData = new Object[varCount][2];
            for (int i = 0; i < varCount; i++) {
                tableData[i][0] = move.getVariable(i);
                tableData[i][1] = null;
            }
        } else {
            tableData = new Object[][]{{null, null}};
        }
        return new MoveEditorTable(tableData, new String[]{"Variable", "Boolean"}, move);
    }

    @Override
    protected JTable setTable() {
        JTable newTable = new JTable(tableModel);
        //tooltip displayed when hovering over the table
        newTable.setToolTipText("Characters not allowed in variables: "
                + DataHandling.IllegalCharsWithSpaces(DataHandling.Illegal.VARIABLE));
        newTable.setPreferredScrollableViewportSize(newTable.getPreferredSize());
        newTable.getTableHeader().setReorderingAllowed(false);
        return newTable;
    }

    /**
     * When the user types into the main text field and presses enter a name
     * change is attempted for the Move. If the change fails nothing happens but
     * the input will remain in the field until changed by the user. When the
     * change succeeds the border displaying the name is redrawn showing the
     * user that the name has been changed.
     *
     * @see bodylog.ui.edit.abstracts.Editor#textField
     */
    @Override
    protected void textFieldAction(String text) {
        try {
            getMove().setName(text);
        } catch (IllegalArgumentException ill) {
            return;
        }
        setEditorBorder("");
    }

    @Override
    public boolean fileExists() {
        return saver.fileExists();
    }

    /**
     * Writes the edited Move into a move file.
     *
     * @see bodylog.files.edit.MoveSaver
     */
    @Override
    protected void saveToFile() {
        try {
            saver.saveToFile();
        } catch (FileAlreadyExistsException fae) {

        } catch (IOException io) {
            Logger.getLogger(MoveEditor.class.getName()).log(Level.SEVERE, null, io);
        } catch (SecurityException sec) {

        } catch (Exception e) {

        }
    }

}
