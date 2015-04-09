package bodylog.ui.tables;

import bodylog.logic.DataHandling;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Set;
import bodylog.ui.dataviewing.MoveTables;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 * TableModel for viewing statistics. Used in MoveTables.
 *
 * @see MoveTables
 */
public class StatTable extends AbstractTableModel {

    protected Move move;
    protected Session session;

    /**
     * Takes data from a Move and a Session.
     *
     * @param session Session whose Sets displayed in the table
     * @param move Move used in this table
     * @see Move
     * @see Session
     */
    public StatTable(Session session, Move move) {
        this.move = move;
        this.session = session;
    }

    /**
     * Returns column name which is a variable of the Move.
     *
     * @param colummn index to get variable
     * @return the variable
     * @see Move#getVariable(int)
     */
    @Override
    public String getColumnName(int colummn) {
        return move.getVariable(colummn);
    }

    /**
     * Returns row count which the number of Sets contained in the Session
     * displayed by this table.
     *
     * @return number of sets
     * @see Session#getSets
     */
    @Override
    public int getRowCount() {
        return session.getSets().size();
    }

    /**
     * Returns column count which is the number of variables in the Move.
     *
     * @return the number of variables
     * @see Move#variableCount
     */
    @Override
    public int getColumnCount() {
        return move.variableCount();
    }

    /**
     * Gets the value at the specified row and column which is a certain value
     * at a certain index at a certain Set.
     *
     * @param row the Set from which to get the value
     * @param column the index for the value in the Set
     * @return the value at the index
     * @see Session#getSet(int)
     * @see Set#getValue(int)
     */
    @Override
    public Object getValueAt(int row, int column) {
        return session.getSet(row).getValue(column);
    }
}
