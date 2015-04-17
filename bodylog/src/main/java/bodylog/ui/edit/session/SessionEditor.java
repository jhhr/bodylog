package bodylog.ui.edit.session;

import bodylog.ui.edit.Editor;
import bodylog.ui.tables.abstracts.EditorTable;
import bodylog.logic.Set;
import bodylog.logic.Session;
import bodylog.files.Constant;
import bodylog.files.Saver;
import bodylog.logic.Move;
import bodylog.ui.tables.edit.SessionEditorTable;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

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

        setButtonLayouts("add set", "remove set",
                "Set date:", "Date must be in form 'dd.mm.yyyy'.",
                ", Session:" + dateStr, "close session");
    }

    @Override
    protected EditorTable setTableModel() {
        return new SessionEditorTable(getMove().getVariables(), 1);
    }

    @Override
    protected JTable setTable() {
        JTable newTable = new JTable(tableModel);
        //sets tooltip for describing what inputs are accepted, displayed when hovering over the table
        newTable.setToolTipText("Allowed values are numbers of the form X or X.X, 'true', 'false' and nothing.");
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
        addSetsToSession();

        try {
            saver.saveToFile();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(getParent(),
                    ex.getCause() + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(SessionEditor.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates the Set objects to be given the the Session of the Move. One set
     * for each row in the table. If nothing was inputted into a cell,
     * <code>null</code> is saved as the value in the proper index of that Set.
     *
     * Should implement a custom TableModel encapsulating this.
     */
    private void addSetsToSession() {
        Session session = saver.getMove().getSession(0);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Set sarja = new Set();
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                sarja.addValue(tableModel.getValueAt(i, j));
            }
            session.addSet(sarja);
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
