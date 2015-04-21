package bodylog.ui.tables.view;

import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.ui.tables.abstracts.MoveTableModel;

/**
 * TableModel for viewing statistics. Used in MoveTables.
 *
 * @see bodylog.ui.dataviewing.MoveTables
 */
public class ViewTable extends MoveTableModel {
    
    protected final Session session;

    public ViewTable(Session session, Move move) {
        super(move);
        this.session = session;
    }

    /**
     * Returns row count which the number of Sets contained in the Session
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
     * Gets the value at the specified row and column which is a certain value
     * at a certain index at a certain Set.
     *
     * @param row the Set from which to get the value
     * @param column the index for the value in the Set
     * @return the value at the index
     * @see bodylog.logic.Session#getSet(int)
     * @see bodylog.logic.Set#getValue(int)
     */
    @Override
    public Object getValueAt(int row, int column) {
        return session.getSet(row).getValue(column);
    }
}
