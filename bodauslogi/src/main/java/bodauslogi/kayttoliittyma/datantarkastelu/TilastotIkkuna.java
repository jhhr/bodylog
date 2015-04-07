package bodauslogi.kayttoliittyma.datantarkastelu;

import bodauslogi.logiikka.Liike;
import bodauslogi.tiedostokasittely.Tiedostosta;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TilastotIkkuna extends JPanel
        implements ListSelectionListener {

    private final JPanel taulukkoPanel;
    private final JList lista;
    private final JSplitPane splitPane;
    private final String[] liikeNimiLista;
    private final HashMap<String, JPanel> taulukot;

    public TilastotIkkuna() throws Exception {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        Liike[] liikeLista = Tiedostosta.kaikkiLiikkeetSessioineen();

        liikeNimiLista = new String[liikeLista.length];
        taulukot = new HashMap<>();

        for (int i = 0; i < liikeLista.length; i++) {
            Liike liike = liikeLista[i];
            String nimi = liike.toString();
            liikeNimiLista[i] = nimi;
            taulukot.put(nimi, new LiikkeenTilastot(liike));
        }

        lista = new JList(liikeNimiLista);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setSelectedIndex(0);
        lista.addListSelectionListener(this);

        JScrollPane listaScrollPane = new JScrollPane(lista);
        taulukkoPanel = new JPanel();

        JScrollPane taulukkoScrollPane = new JScrollPane(taulukkoPanel);

        //Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listaScrollPane, taulukkoScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(100);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(50, 50);
        listaScrollPane.setMinimumSize(minimumSize);
        taulukkoScrollPane.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 400));
        this.add(splitPane);

        if (liikeLista.length == 0) {
            taulukkoPanel.add(new JLabel("Ei löytynyt liikkeitä, mistä näyttää tilastoja"));
        } else {
            paivitaPanel(liikeNimiLista[lista.getSelectedIndex()]);
        }
    }

    //Listens to the list
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        paivitaPanel(liikeNimiLista[list.getSelectedIndex()]);
    }

    protected void paivitaPanel(String liikeNimi) {
        for (Component vanhatTaulukot : taulukkoPanel.getComponents()) {
            taulukkoPanel.remove(vanhatTaulukot);
        }
        taulukkoPanel.add(taulukot.get(liikeNimi));
        taulukkoPanel.validate();
        taulukkoPanel.repaint();
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }
}
