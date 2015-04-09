package bodylog.ui.tables;

import bodylog.logic.DataHandling;
import bodylog.ui.dataediting.SessionEditor;
import java.awt.Container;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * TableModel for creating session data. Used in SessionEditor. Extends
 * DefaultTableModel which already contains the functionality for receiving user
 * input. This causes it to use the obsolete collection Vector. Should create a
 * custom TableModel with editing capability.
 *
 * @see SessionEditor
 */
public class EditorTable extends DefaultTableModel {

    private final Container panel;

    public EditorTable(Object[] columns, int rowCount, Container panel) {
        super(columns, rowCount);
        this.panel = panel;
    }

    /**
     * Sets the value in the specified cell. Checks if the value is acceptable
     * by calling <code>DataHandling.stringToSetValue</code> to parse the input.
     * If the parsing fails and throws an exception, the value is not set into
     * the table. This causes the input to disappear, prompting to user to try
     * again. The table has a ToolTip that describes acceptable inputs.
     *
     * @param value value to be added
     * @param row row
     * @param column column
     *
     * @see DataHandling#stringToSetValue
     */
    @Override
    public void setValueAt(Object value, int row, int column) {
        try {
            value = DataHandling.stringToSetValue((String) value);
        } catch (Exception ex) {
            return;
        }
        Vector rowVector = (Vector) dataVector.elementAt(row);
        rowVector.setElementAt(value, column);
        fireTableCellUpdated(row, column);
    }

}
