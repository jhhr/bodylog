package bodauslogi;

import bodauslogi.logiikka.*;
import bodauslogi.kayttoliittyma.SplitPaneTaulukko;
import bodauslogi.kayttoliittyma.Taulukko;
import bodauslogi.kayttoliittyma.TaulukkoTableModel;
import bodauslogi.tiedostokasittely.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class App {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    SplitPaneTaulukko.createAndShowGUI();
                } catch (Exception e) {
                    System.out.println(e.getMessage() + e.getCause());
                }
            }
        });
    }
}
