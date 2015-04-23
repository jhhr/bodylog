package bodylog.ui.edit.move;

import bodylog.files.abstracts.Saver;
import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
import bodylog.ui.edit.Editor;
import bodylog.logic.datahandling.Names;
import bodylog.logic.exceptions.NameNotAllowedException;
import bodylog.ui.MoveListContainerUpdater;
import bodylog.ui.tables.edit.MoveEditorTable;
import bodylog.ui.tables.abstracts.EditorTable;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

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

    /**
     * Creates a new <code>MoveEditor</code> for the given Saver which contains
     * the Move to be edited which can be a newly created blank one.
     *
     * @param saver
     * @param window Container of this <code>MoveEditor</code>
     */
    public MoveEditor(Saver saver, MoveEditorWindow window) {
        super(saver, window);

        setEditorBorder("");

        setButtonLayouts("add variable", "remove variable",
                "name:", "Characters not allowed in name: "
                + Names.IllegalCharsWithSpaces(Names.Illegal.MOVE_NAME),
                "", "close movement");
    }

    @Override
    protected EditorTable setTableModel() {
        Object[][] tableData;
        int varCount = getMove().variableCount();
        if (varCount > 0) {
            tableData = new Object[varCount][3];
            for (int i = 0; i < varCount; i++) {
                Variable var = getMove().getVariable(i);
                tableData[i][0] = var.getName();
                tableData[i][1] = var.getType();
                tableData[i][2] = Arrays.toString(var.getChoices());
            }
        } else {
            tableData = new Object[][]{{null, null, null}};
            getMove().addVariable(new Variable());
        }
        return new MoveEditorTable(tableData,
                new String[]{"Variable", "Type", "Choices"}, getMove());
    }

    @Override
    protected JTable setTable() {
        JTable newTable = new JTable(tableModel);
        //tooltip displayed when hovering over the table
        newTable.setToolTipText("Characters not allowed in variables: "
                + Names.IllegalCharsWithSpaces(Names.Illegal.VARIABLE));
        newTable.setPreferredScrollableViewportSize(newTable.getPreferredSize());
        newTable.getTableHeader().setReorderingAllowed(false);

        TableColumn varTypeColumn = newTable.getColumnModel().getColumn(1);
        JComboBox typeChooser = new JComboBox();
        typeChooser.addItem(Type.NUMERICAL);
        typeChooser.addItem(Type.CHECKBOX);
        typeChooser.addItem(Type.OPTIONAL_CHOICE);
        typeChooser.addItem(Type.MANDATORY_CHOICE);
        varTypeColumn.setCellEditor(new DefaultCellEditor(typeChooser));

        TableColumn varChoicesColumn = newTable.getColumnModel().getColumn(2);
        varChoicesColumn.setCellEditor(
                new VariableChoicesEditor(saver.getUpdater().getFrame()));
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
        } catch (NameNotAllowedException ill) {
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
