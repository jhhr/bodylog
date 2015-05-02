package bodylog.ui.tables.abstracts;

import bodylog.logic.Move;
import javax.swing.table.AbstractTableModel;

/**
 * Abstract TableModel used a base for TableModel implementations used to edit
 * Moves or Sessions.
 */
public abstract class MoveTableModel extends AbstractTableModel {

    protected Move move;

    /**
     * Creates a new TableModel based on a Move.
     *
     * @param move Move used in this table
     * @see bodylog.logic.Move
     * @see bodylog.logic.Session
     */
    public MoveTableModel(Move move) {
        this.move = move;
    }

    /**
     * Returns column name which is the name of a variable of the Move.
     *
     * @param colummn index to get variable
     *
     * @return the variable
     *
     * @see bodylog.logic.Move#getVariableName
     */
    @Override
    public String getColumnName(int colummn) {
        return move.getVariableName(colummn);
    }

    /**
     * Returns column count which is the number of variables in the Move.
     *
     * @return the number of variables
     *
     * @see bodylog.logic.Move#getVariableCount
     */
    @Override
    public int getColumnCount() {
        return move.getVariableCount();
    }
}
