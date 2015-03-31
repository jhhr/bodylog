
package bodauslogi.kayttoliittyma;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Paaikkuna extends JPanel {
    
    public Paaikkuna() throws Exception{        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        setLayout(new GridLayout(1, 2));
        
        JPanel datanLisays = new JPanel();
        datanLisays.setMaximumSize(new Dimension(200, 500));
        datanLisays.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));        
        datanLisays.add(new JLabel("Ei mitään vielä"));
        this.add(datanLisays);
        
        JScrollPane datanTarkastelu = new JScrollPane(new LiikelistaJaTilastotSplitPane());
        this.add(datanTarkastelu);
    }
    
     public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Bodauslogi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            //Add content to the window.
            frame.add(new Paaikkuna());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage() + ex.getCause(), "Error", JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(Paaikkuna.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
