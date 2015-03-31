package bodauslogi.kayttoliittyma;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import java.util.ListIterator;
import javax.swing.table.AbstractTableModel;

public class TilastoTableModel extends AbstractTableModel {

    private final Object[][] riviData;
    private final String[] muuttujat;

    public TilastoTableModel(Sessio sessio, Liike liike) {
        this.muuttujat = liike.muuttujatToArray();
        this.riviData = new Object[sessio.getSarjat().size()][muuttujat.length];
        for (ListIterator<Sarja> it = sessio.getSarjat().listIterator(); it.hasNext();) {
            this.riviData[it.nextIndex()] = it.next().toArray(muuttujat.length);
        }
    }

    @Override
    public String getColumnName(int sarake) {
        return muuttujat[sarake];
    }

    @Override
    public int getRowCount() {
        return riviData.length;
    }

    @Override
    public int getColumnCount() {
        return muuttujat.length;
    }

    @Override
    public Object getValueAt(int rivi, int sarake) {
        return riviData[rivi][sarake];
    }
}
