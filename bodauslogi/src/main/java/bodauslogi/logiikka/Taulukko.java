package bodauslogi.logiikka;

import java.util.Date;
import javax.swing.table.AbstractTableModel;

public class Taulukko extends AbstractTableModel {

    private Liike liike;
    private Date pvm;

    public Taulukko(Liike liike, Date pvm) {
        this.liike = liike;
        this.pvm = pvm;

    }
    
    public Liike getLiike(){
        return liike;
    }
    
    public Date getPaivamaara(){
        return pvm;
    }

    @Override
    public int getRowCount() {
        return liike.getSarjaLista().size();
    }

    @Override
    public int getColumnCount() {
        return liike.getMuuttujaJoukko().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return liike.getSarjaLista().get(rowIndex).getArvo(columnIndex);
    }
}
