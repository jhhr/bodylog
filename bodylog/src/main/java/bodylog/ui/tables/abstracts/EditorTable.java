package bodylog.ui.tables.abstracts;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Abstract TableModel class acting as a base for TableModels used in editing
 * Moves or Sessions. Used in. Extends DefaultTableModel which already contains
 * the functionality for receiving user input but uses the obsolete collection
 * Vector. Should create a custom TableModel with editing capability.
 *
 * @see bodylog.ui.dataediting.SessionEditor
 */
public abstract class EditorTable extends DefaultTableModel {

    public EditorTable(Object[] columns, int rowCount) {
        super(columns, rowCount);
    }

    public EditorTable(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    /**
     * Sets the value in the specified cell. Checks if the value is acceptable
     * by calling <code>parseValue</code> to parse the input. If the parsing
     * fails and returns null, the value is not set into the table. This causes
     * the input to disappear, prompting to user to try again. The table should
     * have a ToolTip that describes acceptable inputs.
     *
     * @param value value to be added
     * @param row row
     * @param column column
     *
     * @see bodylog.ui.dataediting.SessionEditor
     * @see bodylog.ui.dataediting.MoveEditor
     */
    @Override
    public void setValueAt(Object value, int row, int column) {

        Object parsedValue = parseValue(value, row, column);
        if (parsedValue == null) {
            return;
        }
        Vector rowVector = (Vector) dataVector.elementAt(row);
        rowVector.setElementAt(parsedValue, column);
        fireTableCellUpdated(row, column);
    }

    /**
     * Checks if a row has had data put in. Used for checking the last row
     * before removing it.
     *
     * @param row row to be checked
     * @return true when all values in the row are null, false otherwise
     */
    public boolean rowIsEmpty(int row) {
        for (int i = 0; i < getColumnCount(); i++) {
            if (getValueAt(row, i) != null) {
                return false;
            }
        }
        return true;
    }

    protected abstract Object parseValue(Object value, int row, int column);

}
