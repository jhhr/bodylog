package bodauslogi.kayttoliittyma.taulukot;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sessio;
import javax.swing.table.AbstractTableModel;

public class Taulukko extends AbstractTableModel {

    protected Liike liike;
    protected Sessio sessio;

    public Taulukko(Sessio sessio, Liike liike) {
        this.liike = liike;
        this.sessio = sessio;
    }

    @Override
    public String getColumnName(int sarake) {
        return liike.getMuuttuja(sarake);
    }

    @Override
    public int getRowCount() {
        return sessio.getSarjat().size();
    }

    @Override
    public int getColumnCount() {
        return liike.muuttujaMaara();
    }

    @Override
    public Object getValueAt(int rivi, int sarake) {
        return sessio.getSarja(rivi).getArvo(sarake);
    }
}
