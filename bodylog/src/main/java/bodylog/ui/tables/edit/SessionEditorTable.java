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
import bodylog.ui.tables.abstracts.EditorTable;

public class SessionEditorTable extends EditorTable {

    private final Move move;
    private final Session session;

    public SessionEditorTable(Move move, int rowCount) {
        super(move.getVariableNames(), rowCount);
        this.move = move;
        this.session = move.getSession(0);
    }

    @Override
    public Class getColumnClass(int column) {
        return move.getVariable(column).getAllowedClass();
    }
    
    
    @Override
    public void addRow(Object[] rowData){
        super.addRow(rowData);
        session.addSet(new Set());
    }
    
    @Override
    public void removeRow(int row){
        super.removeRow(row);
        session.removeLastSet();
    }

    @Override
    protected Object parseValue(Object value, int row, int column) {
        Object parsedValue = null;
        try {
            parsedValue = Sets.parseValue((String) value,
                    move.getVariable(column).getChoices());
            session.getSet(row).addValue(parsedValue);
        } catch (Exception ex) {
        }
        return parsedValue;
    }

}
