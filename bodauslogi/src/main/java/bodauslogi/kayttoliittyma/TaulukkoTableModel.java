package bodauslogi.kayttoliittyma;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import javax.swing.table.AbstractTableModel;

public class TaulukkoTableModel extends AbstractTableModel {

    private final Object[][] riviData;
    private final String[] muuttujat;

    public TaulukkoTableModel(Sessio sessio, Liike liike) {
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
