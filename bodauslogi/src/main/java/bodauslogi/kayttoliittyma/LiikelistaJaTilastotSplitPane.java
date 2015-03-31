package bodauslogi.kayttoliittyma;

import bodauslogi.logiikka.Liike;
import bodauslogi.tiedostokasittely.Tiedostosta;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LiikelistaJaTilastotSplitPane extends JPanel
        implements ListSelectionListener {

    private JPanel taulukkoPanel;
    private JList list;
    private JSplitPane splitPane;
    private String[] liikeNimiLista;
    private HashMap<String, JPanel> taulukot;

    public LiikelistaJaTilastotSplitPane() throws Exception {
        
        Liike[] liikeLista = Tiedostosta.kaikkiLiikkeetSessioineen();

        liikeNimiLista = new String[liikeLista.length];
        taulukot = new HashMap<>();

        for (int i = 0; i < liikeLista.length; i++) {
            Liike liike = liikeLista[i];
            String nimi = liike.getNimi();
            liikeNimiLista[i] = nimi;
            taulukot.put(nimi, new LiikkeenTilastotJPanel(liike));
        }

        list = new JList(liikeNimiLista);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

        JScrollPane listScrollPane = new JScrollPane(list);
        taulukkoPanel = new JPanel();

        JScrollPane taulukkoScrollPane = new JScrollPane(taulukkoPanel);

        //Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, taulukkoScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(100);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(50, 50);
        listScrollPane.setMinimumSize(minimumSize);
        taulukkoScrollPane.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 400));
        this.add(splitPane);

        if (liikeLista.length == 0) {
            taulukkoPanel.add(new JLabel("Ei löytynyt liikkeitä, mistä näyttää tilastoja"));
        } else {
            paivitaPanel(liikeNimiLista[list.getSelectedIndex()]);
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
        taulukkoPanel.repaint();
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("Taulukot liikkeistä");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LiikelistaJaTilastotSplitPane splitPane;
        try {
            splitPane = new LiikelistaJaTilastotSplitPane();
            frame.getContentPane().add(splitPane.getSplitPane());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage() + ex.getCause(), "Error", JOptionPane.WARNING_MESSAGE);
        } finally {
        }

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
