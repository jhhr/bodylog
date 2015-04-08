package bodylog.ui.tables;

import bodylog.logic.Move;
import bodylog.logic.Session;
import javax.swing.table.AbstractTableModel;

public class StatTable extends AbstractTableModel {

    protected Move move;
    protected Session session;

    public StatTable(Session session, Move move) {
        this.move = move;
        this.session = session;
    }

    @Override
    public String getColumnName(int colummn) {
        return move.getVariable(colummn);
    }

    @Override
    public int getRowCount() {
        return session.getSets().size();
    }

    @Override
    public int getColumnCount() {
        return move.variableCount();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return session.getSet(row).getValue(column);
    }
}
