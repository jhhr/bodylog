package bodauslogi.tiedostokasittely;

import bodauslogi.kayttoliittyma.TaulukkoTableModel;
import bodauslogi.logiikka.*;
import java.awt.Dimension;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TiedostoistaTaulukot {

    private TiedostoistaTaulukot() {
    }

//    private static Liike luoLiike(File sessioTiedosto) throws Exception {
//        String nimi = sessioTiedosto.getParentFile().getName();
//        File liikeTiedosto = new File(Kansiot.LIIKKEET + "/" + nimi + ".txt");
//        Liike liike = TiedostostaLiike.luo(liikeTiedosto);
//        return liike;
//    }
    public static JPanel luo(String liikeNimi) throws Exception {
        File liikeKansio = new File(Vakiot.DATA + "/" + liikeNimi);
        File liikeTiedosto = new File(Vakiot.LIIKKEET + "/" + liikeNimi + ".txt");
        Liike liike = TiedostostaLiike.luo(liikeTiedosto);
        JPanel taulukkoPanel = new JPanel();
        taulukkoPanel.setLayout(new BoxLayout(taulukkoPanel, BoxLayout.Y_AXIS));
        for (File sessioFilu : liikeKansio.listFiles()) {            
            JTable taulukko = new JTable(new TaulukkoTableModel(TiedostostaSessio.luo(sessioFilu), liike));
            String pvm = sessioFilu.getName();
            pvm = pvm.substring(0, pvm.length() - 4);
            taulukko.setPreferredScrollableViewportSize(new Dimension(taulukko.getColumnCount() * 50, taulukko.getRowCount() * 16));
            taulukko.setFillsViewportHeight(true);
            JScrollPane pane = new JScrollPane(taulukko);
            pane.setBorder(
                    BorderFactory
                    .createCompoundBorder(
                            BorderFactory.createTitledBorder(pvm),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5))
            );
            taulukkoPanel.add(pane);
        }
        return taulukkoPanel;
    }

}
