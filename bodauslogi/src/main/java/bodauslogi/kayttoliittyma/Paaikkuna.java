package bodauslogi.kayttoliittyma;

import bodauslogi.kayttoliittyma.datantarkastelu.TilastotIkkuna;
import bodauslogi.kayttoliittyma.datanlisays.SessionLisaysIkkuna;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class Paaikkuna extends JFrame {

    private JTabbedPane tabit;
    private JMenuBar menuPalkki;

    public Paaikkuna(String otsikko) {
        super(otsikko);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        menuPalkki = new JMenuBar();
        tabit = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);        

        JMenu menu = new JMenu("Menu");
        
        JMenuItem lisays = new JMenuItem("Tallenna sessioita");
        lisays.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tabit.add("Sessionlisäys", new SessionLisaysIkkuna());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(Paaikkuna.class.getName()).log(Level.SEVERE, null, ex);
                }
                tabit.setTabComponentAt(tabit.getTabCount()-1, new SuljettavaTabi(tabit));
            }
        });
        menu.add(lisays);

        JMenuItem tilastot = new JMenuItem("Katso tilastoja");
        tilastot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                try {
                    tabit.add("Tilastot", new JScrollPane(new TilastotIkkuna()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(Paaikkuna.class.getName()).log(Level.SEVERE, null, ex);
                }
                tabit.setTabComponentAt(tabit.getTabCount()-1, new SuljettavaTabi(tabit));                
            }
        });
        menu.add(tilastot);
        
        JMenuItem apua = new JMenuItem("Apua");
        apua.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tabit.add("Apua",new JLabel("apuapapua"));
                tabit.setTabComponentAt(tabit.getTabCount()-1, new SuljettavaTabi(tabit));
            }
        });
        menu.add(apua);
        
        menuPalkki.add(menu);
        setJMenuBar(menuPalkki);
        tabit.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        setContentPane(tabit);
        setSize(new Dimension(600, 600));
        setLocationRelativeTo(null);

//        JScrollPane datanTarkastelu = new JScrollPane(new LiikelistaJaTilastotSplitPane());
    }

    public static void luoJaNaytaIkkuna() {

        JFrame ikkuna = new Paaikkuna("Bodauslogi");

        ikkuna.setVisible(true);
    }

}
