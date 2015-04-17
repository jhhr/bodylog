package bodylog.ui.tables.edit;

import bodylog.logic.DataHandling;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Set;
import bodylog.ui.tables.view.ViewTable;

/**
 * Implementation of a TableModel with row and column adding functionality like
 * DefaultTableModel but without using Vectors. If this is made it will replace
 * EditorTable.
 *
 */
public class CustomEditorTable extends ViewTable {

    public CustomEditorTable(Session session, Move move) {
        super(session, move);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        try {
            value = DataHandling.stringToSetValue((String) value);
        } catch (Exception ex) {
            return;
        }
        session.getSet(row).addValue(value, column);
    }

    /**
     * Adds a row to the end of the model. The new row will contain
     * <code>null</code> values. Notification of the row being added will be
     * generated.
     *
     */
    public void addRow() {
        insertRow(getRowCount());
    }

    /**
     * Inserts a row at <code>row</code> in the model. The new row will contain
     * <code>null</code>. Notification of the row being added will be generated.
     *
     * @param row the row index of the row to be inserted
     * @exception ArrayIndexOutOfBoundsException if the row was invalid
     */
    public void insertRow(int row) {
        session.addSet(new Set());
        justifyRows(row, row + 1);
        fireTableRowsInserted(row, row);
    }

    private void justifyRows(int from, int to) {

        for (int i = from; i < to; i++) {
//            if (session.getSet(i) == null) {
//                session.addSet(new Set(), i);
//            }
            session.getSet(i).addValue(null, getColumnCount());
        }
    }

}
