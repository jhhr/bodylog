/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui.tables.edit;

import bodylog.logic.Move;
import bodylog.ui.tables.abstracts.EditorTable;

public class MoveEditorTable extends EditorTable {

    private Move move;

    public MoveEditorTable(Object[][] data, Object[] columnNames, Move move) {
        super(data, columnNames);
        this.move = move;
    }

//    @Override
//    public Class getColumnClass(int column) {
//        return (column == 0) ? String.class : Boolean.class;
//    }

    @Override
    protected Object parseValue(Object value, int row, int column) {
        if (column == 0) {
            try {
                move.addVariable((String) value, row);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return value;
    }
}
