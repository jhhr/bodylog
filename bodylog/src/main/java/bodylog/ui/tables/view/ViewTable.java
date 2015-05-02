package bodylog.ui.tables.view;

import bodylog.logic.Session;
import javax.swing.table.AbstractTableModel;

/**
 * TableModel for viewing statistics. Used in MoveTables.
 *
 * @see bodylog.ui.view.StatisticsDisplayer
 */
public class ViewTable extends AbstractTableModel {

    private final Session session;

    public ViewTable(Session session) {
        this.session = session;
    }

    @Override
    public Class getColumnClass(int column) {
        return session.getVariable(column).getAllowedClass();
    }

    /**
     * Returns column name which is the name of a variable in the Session.
     *
     * @param colummn index to get variable
     *
     * @return the variable
     *
     * @see bodylog.logic.Move#getVariableName
     */
    @Override
    public String getColumnName(int colummn) {
        return session.getVariableName(colummn);
    }

    /**
     * Returns column count which is the number of variables in the Session.
     *
     * @return the number of variables
     * @see bodylog.logic.Move#getVariableCount
     */
    @Override
    public int getColumnCount() {
        return session.getVariableCount();
    }

    /**
     * Returns row count which is the number of Sets contained in the Session
     * displayed by this table.
     *
     * @return number of sets
     * @see bodylog.logic.Session#getSets
     */
    @Override
    public int getRowCount() {
        return session.getSets().size();
    }

    /**
     * Gets the value at the specified row and column which is a value at the
     * column index in a Set that is at the row index in the Session.
     *
     * @param row the Set from which to get the value
     * @param column the index for the value in the Set
     * @return the value at the index
     * @see bodylog.logic.Session#getSet
     * @see bodylog.logic.Set#getValue
     */
    @Override
    public Object getValueAt(int row, int column) {
        return session.getSet(row).getValue(column);
    }

}
