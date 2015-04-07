package bodauslogi.kayttoliittyma.taulukot;

import bodauslogi.kayttoliittyma.datanlisays.SessionLisays;
import java.awt.Container;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LisaysTaulukko extends DefaultTableModel {

    private final Container panel;

    public LisaysTaulukko(Object[] sarakkeet, int rivimaara, Container panel) {
        super(sarakkeet, rivimaara);
        this.panel = panel;
    }

    @Override
    public void setValueAt(Object arvo, int rivi, int sarake) {
        if (arvo != null) {
            if (arvo.equals("true")) {
                arvo = true;
            } else if (arvo.equals("false")) {
                arvo = false;
            } else {
                try {
                    arvo = Double.parseDouble((String) arvo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Sallitut arvot ovat luvut muodossa X tai X.X sekä merkkijonot 'true' ja 'false'.", "Errpr", JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(SessionLisays.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }
        }

        Vector rowVector = (Vector) dataVector.elementAt(rivi);
        rowVector.setElementAt(arvo, sarake);
        fireTableCellUpdated(rivi, sarake);
    }

}
