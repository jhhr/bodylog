package bodauslogi.kayttoliittyma;

import bodauslogi.tiedostokasittely.TiedostoistaTaulukot;
import bodauslogi.tiedostokasittely.Vakiot;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.File;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.View;
import sun.swing.JLightweightFrame;

public class SplitPaneTaulukko extends JPanel
        implements ListSelectionListener {

    private JPanel taulukkoPanel;
    private JList list;
    private JSplitPane splitPane;
    private String[] liikeNimiLista;
    private HashMap<String, JPanel> taulukot;

    public SplitPaneTaulukko() throws Exception {
        String[] liikeLista = new File(Vakiot.LIIKKEET).list();
        for (int i = 0; i < liikeLista.length; i++) {
            String tiedostoNimi = liikeLista[i];
            liikeLista[i] = tiedostoNimi.substring(0,tiedostoNimi.length()-4);
        }        

        taulukot = new HashMap<>();
        for (String nimi : liikeLista) {
            taulukot.put(nimi, TiedostoistaTaulukot.luo(nimi));
        }

        //Create the list of images and put it in a scroll pane.
        this.taulukot = taulukot;
        this.liikeNimiLista = liikeLista;
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
        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
        taulukkoScrollPane.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 400));
        updateLabel(liikeNimiLista[list.getSelectedIndex()]);
    }

    //Listens to the list
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        updateLabel(liikeNimiLista[list.getSelectedIndex()]);
    }

    //Renders the selected image
    protected void updateLabel(String liikeNimi) {
        for (Component vanhatTaulukot : taulukkoPanel.getComponents()) {
            taulukkoPanel.remove(vanhatTaulukot);
        }
        taulukkoPanel.add(taulukot.get(liikeNimi));
        taulukkoPanel.repaint();
    }

    //Used by SplitPaneDemo2
    public JList getLiikeLista() {
        return list;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI() throws Exception {

        //Create and set up the window.
        JFrame frame = new JFrame("Taulukot liikkeistä");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SplitPaneTaulukko splitPaneDemo = new SplitPaneTaulukko();
        frame.getContentPane().add(splitPaneDemo.getSplitPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
