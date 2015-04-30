package bodylog.ui.edit.session;

import bodylog.ui.edit.Editor;
import bodylog.ui.tables.abstracts.EditorTable;
import bodylog.files.Constant;
import bodylog.files.abstracts.Saver;
import bodylog.logic.Variable;
import bodylog.ui.tables.edit.SessionEditorTable;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 * The UI component used in creating actual training data and writing it into
 * session files. Contained in a SessionEditorWindow. Implementation of the
 * abstract class Editor. Data editing is done in a JTable using EditorTable as
 * its TableModel. Has buttons for adding and removing sets and a field for
 * setting the date for the session.
 *
 * @see bodylog.logic.Move
 * @see bodylog.logic.Session
 * @see bodylog.logic.Set
 * @see bodylog.ui.edit.session.SessionEditorWindow
 * @see bodylog.ui.dataediting.Editor
 * @see bodylog.ui.tables.abstracts.EditorTable
 */
public class SessionEditor extends Editor {

    private static final String ADD_SET_TITLE = "Add Set";
    private static final String REMOVE_SET_TITLE = "Remove Set";

    private String dateStr;

    /**
     * Creates a new SessionEditor for the given Saver which contains the Move
     * to be edited. The date is shown in the border surrounding this component
     * and is changed with <code>setEditorBorder</code>.
     *
     * @param saver
     * @param editorWindow Parent container of this SessionEditor
     * @see bodylog.ui.dataediting.Editor#setEditorBorder
     */
    public SessionEditor(Saver saver, SessionEditorWindow editorWindow) {
        super(saver, editorWindow);
        dateStr = saver.getMove().getSession(0).getUIDateString();

        setEditorBorder(" " + dateStr);

        setButtonLayouts(
                ADD_SET_TITLE, 
                REMOVE_SET_TITLE,
                "Set date:", 
                dateStr, 
                "Press Enter to enact change, use the same form as displayed.",
                ", Session:" + dateStr);
    }

    @Override
    protected EditorTable setTableModel() {
        return new SessionEditorTable(getMove());
    }

    @Override
    protected JTable setTable() {
        JTable newTable = new JTable(tableModel) {
            //Implement table header tool tips.
            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    @Override
                    public String getToolTipText(MouseEvent e) {
                        java.awt.Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex
                                = columnModel.getColumn(index).getModelIndex();
                        return getMove().getVariable(realIndex).getToolTip();
                    }
                };
            }
        };
        for (int i = 0; i < getMove().getVariableCount(); i++) {
            Variable var = getMove().getVariable(i);
            JComboBox box = new JComboBox(var.getChoices());
            switch (var.getType()) {
                case OPTIONAL_CHOICE:
                    box.addItem(Variable.OPT_NO_CHOICE);
                case MANDATORY_CHOICE:
                    box.setSelectedIndex(box.getItemCount() - 1);
                    newTable.getColumnModel().getColumn(i).setCellEditor(
                            new DefaultCellEditor(box));
                default:
                    break;
            }
        }

        newTable.setPreferredScrollableViewportSize(newTable.getPreferredSize());
        newTable.getTableHeader().setReorderingAllowed(false);
        return newTable;
    }

    /**
     * When the user types into the main text field it is parsed with a
     * <code>DateTimeFormatter</code>. If the parsing fails the date of this
     * Editor is not changed, though the input will remain in the field until
     * changed by the user. When the date is changed the border displaying the
     * date is redrawn.
     *
     * @see bodylog.ui.edit.abstracts.Editor#textField
     */
    @Override
    protected void textFieldAction(String text) {
        try {
            //try to set new date for the Session
            saver.getMove().getSession(0).setDate(
                    Constant.UI_DATE_FORMAT.parse(text));
        } catch (DateTimeParseException ex) {
            //do nothing if input is not proper
            return;
        }
        dateStr = text;
        setEditorBorder(" " + dateStr);
    }

    /**
     * Writes the inputted session data into a session file using the
     * SessionSaver. First converts the table data into Sets, gives those a
     * created Session with the date of this SessionEditor and gives these to
     * the Move of this SessionEditor.The attempt may throw an exception which
     * is handled by showing a pup-up message.
     *
     * @see bodylog.files.SessionSaver#saveToFile
     * @see bodylog.ui.dataediting.SessionEditor#addSessionsToMove
     */
    @Override
    protected void saveToFile() {

        try {
            saver.saveToFile();
        } catch (Exception unexpected) {
            JOptionPane.showMessageDialog(getParent(),
                    "cause: " + unexpected.getCause()
                    + " message: " + unexpected.getMessage(),
                    "Saving failed unexpectedly", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(this.getClass().getName()).log(
                    Level.SEVERE, null, unexpected);
        }
    }

    /**
     * Checks whether a session file with the same date as this SessionEditor
     * already exists.
     *
     * @return true if the file exists, false otherwise
     * @see bodylog.files.SessionSaver#fileExists
     */
    @Override
    protected boolean fileExists() {
        return saver.fileExists();
    }
}
