/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui.tables.edit;

import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Set;
import bodylog.logic.datahandling.Sets;
import bodylog.logic.exceptions.ParsingException;
import bodylog.ui.tables.abstracts.EditorTable;

public class SessionEditorTable extends EditorTable {

    private final Move move;
    private final Session session;

    /**
     * Creates a new table model for editing a Session of a Move. Adds a new
     * Session to the Move and a Set to the Session.
     *
     * @param move
     */
    public SessionEditorTable(Move move) {
        super(move.getVariableNames(), 1);
        this.move = move;
        this.session = new Session();
        move.addSession(session);
        session.addSet(new Set());
    }

    public SessionEditorTable(Move move, Session session) {
        super(createTableData(session), move.getVariableNames());
        this.move = move;
        this.session = session;
    }
    
    public static Object[][] createTableData(Session editSes){
        int setCount = editSes.getSetCount();
        Object[][] tableData = new Object[setCount][editSes.getVariableCount()];
        for (int i = 0; i < setCount; i++) {
            tableData[i] = editSes.getSet(i).toArray();
        }
        return tableData;
    }

    @Override
    public Class getColumnClass(int column) {
        return move.getVariable(column).getAllowedClass();
    }

    @Override
    public void addRow(Object[] rowData) {
        super.addRow(rowData);
        session.addSet(new Set());
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
        session.removeSet(row);
    }

    @Override
    protected Object parseValue(Object value, int row, int column) {
        Object parsedValue;
        if (getColumnClass(column) == String.class) {
            try {
                parsedValue = Sets.parseValue((String) value,
                        move.getVariable(column));
                session.getSet(row).setValue(parsedValue, column);
            } catch (NumberFormatException | ParsingException ex) {
            }
        } else {
            session.getSet(row).setValue(value, column);
        }
        return value;
    }

}
