package bodauslogi.kayttoliittyma.datanlisays;

import bodauslogi.logiikka.Liike;
import bodauslogi.tiedostokasittely.Tiedostosta;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class SessionLisaysIkkuna extends JScrollPane implements ActionListener {

    private final JPanel valikkoPanel;
    private final JPanel lisaysPanel;
    private final JLabel tyhjaLisays;

    public SessionLisaysIkkuna() throws Exception {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        valikkoPanel = new JPanel();
        lisaysPanel = new JPanel();        
        tyhjaLisays = new JLabel("Ei liikkeitä valittuna");

        valikkoPanel.setLayout(new BoxLayout(valikkoPanel, BoxLayout.X_AXIS));
        valikkoPanel.add(new JLabel("Valitse liike:"));
        JComboBox liikeValikko = new JComboBox(Tiedostosta.liikeLista());
        liikeValikko.addActionListener(this);
        valikkoPanel.add(liikeValikko);

        lisaysPanel.setLayout(new BoxLayout(lisaysPanel, BoxLayout.Y_AXIS));
        lisaysPanel.add(tyhjaLisays);

        GridBagConstraints rajat = new GridBagConstraints();
        rajat.fill = GridBagConstraints.HORIZONTAL;
        rajat.weightx = 1.0;
        rajat.insets = new Insets(5, 5, 5, 5);
        rajat.gridx = 0;
        rajat.gridy = 0;
        panel.add(valikkoPanel, rajat);

        rajat = new GridBagConstraints();
        rajat.fill = GridBagConstraints.HORIZONTAL;
        rajat.weightx = 1.0;
        rajat.insets = new Insets(5, 5, 5, 5);
        rajat.gridx = 0;
        rajat.gridy = 1;
        panel.add(new JSeparator(), rajat);

        rajat = new GridBagConstraints();
        rajat.insets = new Insets(5, 5, 5, 5);
        rajat.gridx = 0;
        rajat.gridy = 2;
        rajat.weighty = 1.0;
        rajat.anchor = GridBagConstraints.PAGE_START;
        panel.add(lisaysPanel, rajat);

        this.setViewportView(panel);
    }

    private void paivitaLisaysPanel(Liike liike) {
        lisaysPanel.remove(tyhjaLisays);

        if (liikkeellaEiMuuttujia(liike) || liikkeellaAvattuSessionLisays(liike)) {
            return;
        }
        lisaysPanel.add(new SessionLisays(liike, this));
        this.validate();
        this.repaint();
    }

    private boolean liikkeellaEiMuuttujia(Liike liike) {
        if (liike.muuttujaMaara() == 0) {
            JOptionPane.showMessageDialog(this, "Liikkeellä ei ole yhtään muuttujaa", "Ei onnistu", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    private boolean liikkeellaAvattuSessionLisays(Liike liike) {
        Component[] sessionLisaykset = lisaysPanel.getComponents();
        for (Component komponentti : sessionLisaykset) {
            SessionLisays sessionLisays = (SessionLisays) komponentti;
            if (sessionLisays.getLiike().equals(liike)) {
                JOptionPane.showMessageDialog(this, "Liikkeestä on jo auki sessionlisäys", "Ei onnistu", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox valikko = (JComboBox) e.getSource();
        Liike liike = (Liike) valikko.getSelectedItem();
        try {
            paivitaLisaysPanel(liike);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(SessionLisaysIkkuna.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void poistaSessionLisays(Component komponentti){
        lisaysPanel.remove(komponentti);
        if (lisaysPanel.getComponentCount() == 0) {
            lisaysPanel.add(tyhjaLisays);
        }
        validate();
        repaint();
    }
}
