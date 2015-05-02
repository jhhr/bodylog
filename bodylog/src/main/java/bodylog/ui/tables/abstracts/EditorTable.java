package bodylog.ui.tables.abstracts;

import bodylog.logic.Move;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Abstract TableModel class acting as a base for TableModels used in editing
 * Moves or Sessions. Used in. Extends DefaultTableModel which already contains
 * the functionality for receiving user input but uses the obsolete collection
 * Vector. Should create a custom TableModel with editing capability.
 *
 * @see bodylog.ui.edit.session.SessionEditor
 */
public abstract class EditorTable extends DefaultTableModel {

    protected final Object[] defaultRowData;
    protected final Move move;

    public EditorTable(Object[][] data, Object[] columnNames, Move move) {
        super(data, columnNames);
        this.move = move;
        defaultRowData = defaultRowData();
    }

    /**
     * Sets the value in the specified cell. Checks if the value is acceptable
     * by calling <code>parseValue</code> to parse the input. If the parsing
     * fails and returns null, the value is not set into the table. This causes
     * the input to disappear, prompting to user to try again. The table should
     * have a ToolTip that describes acceptable inputs.
     *
     * @param value the value to be added
     * @param row the row
     * @param column the column
     *
     * @see bodylog.ui.edit.session.SessionEditor
     * @see bodylog.ui.edit.move.MoveEditor
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
     * Checks if a row has been changed from the default state. Used for
     * checking the last row before removing it.
     *
     * @param row the row to be checked
     *
     * @return true when all values in the row are null, false otherwise
     */
    public boolean rowHasBeenEdited(int row) {
        Object rowValue;
        Object defaultValue;
        for (int i = 0; i < getColumnCount(); i++) {
            rowValue = getValueAt(row, i);
            defaultValue = defaultRowData[i];
            if (rowValue == null) {
                if (defaultValue != null) {
                    return false;
                }
            } else if (!rowValue.equals(defaultValue)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Defines the method with which input into cells is parsed into proper
     * values for this table. A failed parsed is signaled by returning null.
     *
     * @param value the input to be parsed
     * @param row the row of the cell where the input originates
     * @param column the column of the cell where the input originates
     *
     * @return the parsed value, null if parsing fails
     */
    protected abstract Object parseValue(Object value, int row, int column);

    /**
     * Sets the default array for a new row.
     *
     * @return an array with default values
     */
    protected abstract Object[] defaultRowData();

    /**
     * Adds a new row to the table model and executes an action associated with
     * that.
     */
    public void addRow() {
        super.addRow(defaultRowData);
        addRowAction();
    }

    /**
     * The action taken by this when a row is added.
     */
    public abstract void addRowAction();

}
