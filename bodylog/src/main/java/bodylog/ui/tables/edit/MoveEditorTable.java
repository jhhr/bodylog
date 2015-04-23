/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui.tables.edit;

import bodylog.logic.Move;
import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
import bodylog.logic.datahandling.Variables;
import bodylog.ui.tables.abstracts.EditorTable;

public class MoveEditorTable extends EditorTable {

    private Move move;

    public MoveEditorTable(Object[][] data, Object[] columnNames, Move move) {
        super(data, columnNames);
        this.move = move;
    }

    @Override
    public Class getColumnClass(int column) {
        return String.class;
    }

    @Override
    public void addRow(Object[] rowData) {
        super.addRow(rowData);
        move.addVariable(new Variable());
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
    }

    @Override
    protected Object parseValue(Object value, int row, int column) {
        switch (column) {
            case 0:
                try {
                    move.getVariable(row).setName((String) value);
                } catch (Exception e) {
                }
            case 1:
                try {
                    move.getVariable(row).setType(Variables.parseType((String) value));
                } catch (Exception e) {
                }
        }
        return value;
    }
}
