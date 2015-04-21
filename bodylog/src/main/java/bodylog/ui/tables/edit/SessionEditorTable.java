/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui.tables.edit;

import bodylog.ui.tables.abstracts.EditorTable;

public class SessionEditorTable extends EditorTable {

    public SessionEditorTable(Object[] columns, int rowCount) {
        super(columns, rowCount);
    }

    @Override
    protected Object parseValue(Object value, int row, int column) {
        Object parsedValue = null;
        try {
            parsedValue = ;
        } catch (NumberFormatException ex) {
        }
        return parsedValue;
    }

}
