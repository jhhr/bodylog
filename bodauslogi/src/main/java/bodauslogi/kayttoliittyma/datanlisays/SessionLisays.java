package bodauslogi.kayttoliittyma.datanlisays;

import bodauslogi.kayttoliittyma.taulukot.LisaysTaulukko;
import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import bodauslogi.tiedostokasittely.Tallenna;
import bodauslogi.util.Vakiot;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SessionLisays extends JPanel implements ActionListener {

    private final Liike liike;
    private final DefaultTableModel taulukkoModel;
    private final JScrollPane taulukkoPane;
    private final JTable taulukko;
    private final JPanel napitYla;
    private final JPanel napitVasen;
    private final SessionLisaysIkkuna lisaysIkkuna;
    private boolean tallennettu;
    private String paivamaara;

    public SessionLisays(Liike liike, SessionLisaysIkkuna lisaysIkkuna) {
        setLayout(new GridBagLayout());
        this.liike = liike;
        this.tallennettu = false;
        this.napitYla = new JPanel();
        this.napitVasen = new JPanel();
        this.lisaysIkkuna = lisaysIkkuna;
        this.paivamaara = Vakiot.UIPVM.format(LocalDate.now());

        taulukkoModel = new LisaysTaulukko(liike.muuttujatToArray(), 1, this);
        taulukko = new JTable(taulukkoModel);
        taulukko.setPreferredScrollableViewportSize(taulukko.getPreferredSize());
        taulukko.setFillsViewportHeight(false);
        taulukko.getTableHeader().setReorderingAllowed(false);

        taulukkoPane = new JScrollPane(taulukko);
        asetaReunat();
        
        GridBagConstraints rajat = new GridBagConstraints();
        rajat.gridx = 0;
        rajat.gridy = 1;
        rajat.anchor = GridBagConstraints.FIRST_LINE_START;
        add(napitVasen, rajat);

        rajat = new GridBagConstraints();
        rajat.gridx = 1;
        rajat.gridy = 1;
        rajat.weightx = 1.0;
        rajat.insets = new Insets(5, 5, 5, 5);
        rajat.anchor = GridBagConstraints.FIRST_LINE_START;
        add(taulukkoPane, rajat);

        rajat = new GridBagConstraints();
        rajat.gridx = 0;
        rajat.gridy = 0;
        rajat.gridwidth = 2;
        add(napitYla, rajat);

        napitVasen.setLayout(new GridLayout(2, 1));
        napitVasen.add(sarjanLisaysNappi());
        napitVasen.add(sarjanPoistoNappi());

        napitYla.setLayout(new GridLayout(1, 3));
        napitYla.add(paivamaaranAsetusNappi());
        napitYla.add(tallennusNappi());
        napitYla.add(sulkuNappi());
    }

    private JButton sarjanLisaysNappi() {
        JButton sarjanLisaysNappi = new JButton("lisää sarja");
        sarjanLisaysNappi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                taulukkoModel.addRow(new Object[taulukkoModel.getColumnCount()]);
                muutaKokoaJaPaivita();
            }
        });
        return sarjanLisaysNappi;
    }

    private JButton sarjanPoistoNappi() {
        JButton sarjanPoistoNappi = new JButton("poista sarja");
        sarjanPoistoNappi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (taulukkoModel.getRowCount() != 1) {
                    taulukkoModel.removeRow(taulukkoModel.getRowCount() - 1);
                    return;
                }
                muutaKokoaJaPaivita();
            }
        });
        return sarjanPoistoNappi;
    }

    private JPanel paivamaaranAsetusNappi() {
        JPanel paivamaaranAsetus = new JPanel();
        paivamaaranAsetus.add(new JLabel("Aseta päivämäärä:"));
        JTextField tekstiAlue = new JTextField(paivamaara);
        tekstiAlue.setToolTipText("Päivämäärä oltava muodossa 'pp.kk.vvvv'.");
        tekstiAlue.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tekstiAlue = (JTextField) e.getSource();
                String teksti = tekstiAlue.getText();
                try {
                    Vakiot.UIPVM.parse(teksti);
                } catch (DateTimeParseException ex) {                    
//                    JOptionPane.showMessageDialog(getParent(), "Päivämäärä oltava muodossa 'pp.kk.vvvv'.", "Error", JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(SessionLisays.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                paivamaara = teksti;
                asetaReunat();
            }
        });
        paivamaaranAsetus.add(tekstiAlue);
        return paivamaaranAsetus;
    }
    
    private void asetaReunat(){
        setBorder(
                BorderFactory
                .createCompoundBorder(
                        BorderFactory.createTitledBorder(liike + " " + paivamaara),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );
    }

    private JButton tallennusNappi() {
        JButton tallennusNappi = new JButton("tallenna sessio");
        tallennusNappi.addActionListener(new TallenusNapinKuuntelija());
        return tallennusNappi;
    }

    private JButton sulkuNappi() {
        JButton sulkuNappi = new JButton("sulje sessio");
        sulkuNappi.addActionListener(this);
        return sulkuNappi;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        lisaysIkkuna.poistaSessionLisays(this);
    }

    private class TallenusNapinKuuntelija implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (varmistaTallennusKayttajalta()) {
                return;
            }

            lisaaSessiotLiikkeeseen();

            try {
                Tallenna.sessiot(liike);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getParent(), ex.getCause() + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(SessionLisays.class.getName()).log(Level.SEVERE, null, ex);
            }

            tallennettu = true;
        }

        private void lisaaSessiotLiikkeeseen() {
            Sessio sessio = new Sessio();
            for (int i = 0; i < taulukkoModel.getRowCount(); i++) {
                Sarja sarja = new Sarja();
                for (int j = 0; j < taulukkoModel.getColumnCount(); j++) {
                    sarja.lisaaArvo(taulukkoModel.getValueAt(i, j));
                }
                sessio.lisaaSarja(sarja);
            }
            liike.lisaaSessio(sessio);
        }

        private boolean varmistaTallennusKayttajalta() {
            if (tallennettu) {
                String viesti = "Tälle päivämäärälle on jo tallennettu sessio.\n"
                        + "Uudestaan tallentaminen ylikirjoittaa aiemman sessiotiedoston.\n"
                        + "Tallenna sessio silti?";
                Object[] vaihtoehdot = {"Kyllä", "Ei"};
                int kayttajanValinta = JOptionPane.showOptionDialog(
                        getParent(), viesti, "Varmista tallennus",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, vaihtoehdot, vaihtoehdot[1]);

                return kayttajanValinta != JOptionPane.YES_OPTION;
            } else {
                return false;
            }
        }
    }

    private void muutaKokoaJaPaivita() {
        Dimension d = taulukko.getPreferredSize();
        taulukkoPane.setPreferredSize(new Dimension(d.width + 20, d.height + 53));
        revalidate();
        repaint();
    }

    public Liike getLiike() {
        return liike;
    }

}
