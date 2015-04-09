package bodylog.ui.tables;

import bodylog.logic.DataHandling;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Set;
import java.io.Serializable;

/**
 * Implementation of a TableModel similar to DefaultTableModel but without using
 * Vectors. To be done maybe.
 *
 */
public class CustomEditorTable extends StatTable implements Serializable{

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
     *  Adds a row to the end of the model.  The new row will contain
     *  <code>null</code> values unless <code>rowData</code> is specified.
     *  Notification of the row being added will be generated.
     *
     * @param   rowData          optional data of the row being added
     */
    public void addRow() {
        insertRow(getRowCount());
    }
    
    /**
     *  Inserts a row at <code>row</code> in the model.  The new row
     *  will contain <code>null</code> values unless <code>rowData</code>
     *  is specified.  Notification of the row being added will be generated.
     *
     * @param   row             the row index of the row to be inserted
     * @param   rowData         optional data of the row being added
     * @exception  ArrayIndexOutOfBoundsException  if the row was invalid
     */
    public void insertRow(int row) {
        session.addSet(new Set());
        justifyRows(row, row+1);
        fireTableRowsInserted(row, row);
    }
    
    private void justifyRows(int from, int to) {

        for (int i = from; i < to; i++) {
            if (session.getSet(i) == null) {
                //add new set at the index?
            }
            session.getSet(i).addValue(null, getColumnCount());
        }
    }

}
