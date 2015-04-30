package bodylog.ui.edit.move;

import bodylog.files.abstracts.Saver;
import bodylog.logic.Variable.Type;
import bodylog.ui.edit.Editor;
import bodylog.logic.datahandling.Names;
import bodylog.logic.exceptions.DuplicateVariableNameException;
import bodylog.logic.exceptions.FileCreationException;
import bodylog.logic.exceptions.FileRenameException;
import bodylog.logic.exceptions.NameNotAllowedException;
import bodylog.logic.exceptions.VariableStateException;
import bodylog.ui.tables.edit.MoveEditorTable;
import bodylog.ui.tables.abstracts.EditorTable;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

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
    
    private static final String ADD_VAR_TITLE = "Add Variable";
    private static final String REMOVE_VAR_TITLE = "Remove Variable";
    
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

        setButtonLayouts(
                ADD_VAR_TITLE, 
                REMOVE_VAR_TITLE,
                "name:",
                getMove().getName(), 
                "Characters not allowed in name: "
                + Names.IllegalCharsWithSpaces(Names.Illegal.MOVE_NAME),
                "");
    }

    @Override
    protected EditorTable setTableModel() {
        return new MoveEditorTable(getMove());
    }

    @Override
    protected JTable setTable() {
        JTable newTable = new JTable(tableModel);
        //tooltip displayed when hovering over the table
        newTable.setToolTipText("Characters not allowed in name or choices: "
                + Names.IllegalCharsWithSpaces(Names.Illegal.VARIABLE));
        newTable.setRowHeight(20);

        TableColumnModel model = newTable.getColumnModel();
        model.getColumn(0).setPreferredWidth(80);

        TableColumn varTypeColumn = model.getColumn(1);
        varTypeColumn.setPreferredWidth(100);
        JComboBox typeChooser = new JComboBox();
        typeChooser.addItem(Type.NUMERICAL);
        typeChooser.addItem(Type.CHECKBOX);
        typeChooser.addItem(Type.OPTIONAL_CHOICE);
        typeChooser.addItem(Type.MANDATORY_CHOICE);
        typeChooser.setSelectedItem(Type.NUMERICAL);
        varTypeColumn.setCellEditor(new DefaultCellEditor(typeChooser));

        TableColumn varChoicesColumn = model.getColumn(2);
        varChoicesColumn.setPreferredWidth(150);
        varChoicesColumn.setCellEditor(new VariableChoicesEditor(newTable));

        varChoicesColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                setText((value == null) ? "" : Arrays.toString((String[]) value));
            }
        });
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
        } catch (NameNotAllowedException ill) {
            return;
        }
        setEditorBorder("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        window.removeEditor(this);
        getMove().clearSessions();
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
        } catch (IllegalArgumentException | VariableStateException |
                FileCreationException | FileRenameException |
                DuplicateVariableNameException expected) {

            JOptionPane.showMessageDialog(getParent(),
                    expected.getMessage(),
                    "Saving canceled", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception unexpected) {
            JOptionPane.showMessageDialog(getParent(),
                    "cause: " + unexpected.getCause()
                    + " message: " + unexpected.getMessage(),
                    "Saving failed unexpectedly", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(this.getClass().getName()).log(
                    Level.SEVERE, null, unexpected);
        }
    }

}
