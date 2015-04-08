package bodylog.ui.tables;

import bodylog.ui.dataediting.SessionEditor;
import bodylog.logic.DataHandling;
import java.awt.Container;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EditorTable extends DefaultTableModel {

    private final Container panel;

    public EditorTable(Object[] columns, int rowCount, Container panel) {
        super(columns, rowCount);
        this.panel = panel;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
                try {
                    value = DataHandling.stringToSetValue((String) value);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Sallitut arvot ovat luvut muodossa X tai X.X sekä merkkijonot 'true' ja 'false'.", "Error", JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(SessionLisays.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
        Vector rowVector = (Vector) dataVector.elementAt(row);
        rowVector.setElementAt(value, column);
        fireTableCellUpdated(row, column);
    }

}
