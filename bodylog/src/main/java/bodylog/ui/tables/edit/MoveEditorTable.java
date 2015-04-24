/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui.tables.edit;

import bodylog.logic.Move;
import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
import bodylog.ui.tables.abstracts.EditorTable;
import java.util.ArrayList;

public class MoveEditorTable extends EditorTable {

    private final Move move;

    public MoveEditorTable(Move move) {
        super(createTableData(move), new String[]{"Name", "Type", "Choices"});
        this.move = move;
    }
    
    public static Object[][] createTableData(Move move){
        Object[][] tableData;
        int varCount = move.getVariableCount();
        if (varCount > 0) {
            tableData = new Object[varCount][3];
            for (int i = 0; i < varCount; i++) {
                Variable var = move.getVariable(i);
                tableData[i][0] = var.getName();
                tableData[i][1] = var.getType();
                tableData[i][2] = var.getChoices();
            }
        } else {
            tableData = new Object[][]{{"", Variable.Type.NUMERICAL, new String[0]}};
            move.addVariable(new Variable());
        }
        return tableData;
    }

    @Override
    public Class getColumnClass(int column) {
        return column == 0 ? String.class : ArrayList.class;
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
        Variable var = move.getVariable(row);
        switch (column) {
            case 0:
                try {
                    var.setName((String) value);
                } catch (Exception e) {
                }
                break;
            case 1:
                try {
                    var.setType((Type) value);
                } catch (Exception e) {
                }
                break;
            case 2:
                var.setChoices((String[]) value);
                break;
        }
        return value;
    }
}
