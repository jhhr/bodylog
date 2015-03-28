package bodauslogi.kayttoliittyma;

import bodauslogi.logiikka.*;
import bodauslogi.tiedostokasittely.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Taulukko extends JPanel {

    private boolean DEBUG = false;

    public Taulukko(String liikeNimi) throws Exception {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        File liikeKansio = new File(Vakiot.DATA + "/" + liikeNimi);
        File liikeTiedosto = new File(Vakiot.LIIKKEET + "/" + liikeNimi + ".txt");
        Liike liike = TiedostostaLiike.luo(liikeTiedosto);

        for (File sessioFilu : liikeKansio.listFiles()) {
            JTable taulukko = new JTable(new TaulukkoTableModel(TiedostostaSessio.luo(sessioFilu), liike));
            taulukko.setPreferredScrollableViewportSize(new Dimension(taulukko.getColumnCount() * 50, taulukko.getRowCount() * 16));
            taulukko.setFillsViewportHeight(true);
            JScrollPane pane = new JScrollPane(taulukko);

            String pvm = sessioFilu.getName();
            pvm = pvm.substring(0, pvm.length() - 4);

            pane.setBorder(
                    BorderFactory
                    .createCompoundBorder(
                            BorderFactory.createTitledBorder(pvm),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5))
            );
            this.add(pane);
        }

        if (DEBUG) {
            for (final Component osa : this.getComponents()) {
                osa.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        printDebugData(osa);
                    }
                });
            }
        }

    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI(String liikeNimi) throws Exception{
        //Create and set up the window.
        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Taulukko newContentPane = new Taulukko(liikeNimi);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
