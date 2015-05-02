/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui.tables.edit;

import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Set;
import bodylog.ui.tables.abstracts.EditorTable;

public class SessionEditorTable extends EditorTable {

    /**
     * Creates a new table model for editing a Session of a Move. Adds a new
     * Session to the Move and a Set to the Session.
     *
     * @param move the Move to be edited
     */
    public SessionEditorTable(Move move) {
        super(createTableData(move), move.getVariableNames(), move);
    }

    public static Object[][] createTableData(Move move) {
        Session session;
        int setCount;
        if (move.getSessionCount() == 0) {//adding a new session for a move
            session = new Session();
            session.setVariables(move.getVariables());
            move.addSession(session);
            session.addSet(new Set(move.getDefaultValues()));
            setCount = 1;
        } else {//editing a session saved in the past
            session = move.getSession(0);
            setCount = session.getSetCount();
        }
        Object[][] tableData = new Object[setCount][session.getVariableCount()];
        for (int i = 0; i < setCount; i++) {
            tableData[i] = session.getSet(i).toArray();
        }
        return tableData;
    }

    private Session getSession() {
        return move.getSession(0);
    }

    @Override
    public Class getColumnClass(int column) {
        return move.getVariable(column).getAllowedClass();
    }

    @Override
    public void addRowAction() {
        getSession().addSet(new Set(defaultRowData));
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
        getSession().removeSet(row);
    }

    @Override
    protected Object parseValue(Object value, int row, int column) {
        getSession().getSet(row).setValue(value, column);
        return value;
    }

    @Override
    protected Object[] defaultRowData() {
        return move.getDefaultValues();
    }

}
