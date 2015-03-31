package bodauslogi.kayttoliittyma;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sessio;
import bodauslogi.tiedostokasittely.Tiedostosta;
import bodauslogi.util.Vakiot;
import bodauslogi.util.sessioFiltteri;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class LiikkeenTilastotJPanel extends JPanel {

    public LiikkeenTilastotJPanel(Liike liike) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ArrayList<Sessio> sessiot = liike.getSessiot();
        if (sessiot.isEmpty()) {
            this.add(new JLabel("Ei löytynyt sessioita tälle liikkeelle"));
        } else {
            for (Sessio sessio : liike.getSessiot()) {
                JTable taulukko = new JTable(new TilastoTableModel(sessio, liike));
                taulukko.setPreferredScrollableViewportSize(new Dimension(taulukko.getColumnCount() * 50, taulukko.getRowCount() * 16));
                taulukko.setFillsViewportHeight(true);
                JScrollPane pane = new JScrollPane(taulukko);

                String pvm = sessio.getPaivamaaraString();

                pane.setBorder(
                        BorderFactory
                        .createCompoundBorder(
                                BorderFactory.createTitledBorder(pvm),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5))
                );
                this.add(pane);
            }
        }
    }
}
