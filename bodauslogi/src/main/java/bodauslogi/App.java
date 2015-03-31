package bodauslogi;

import bodauslogi.kayttoliittyma.*;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {

    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                    LiikelistaJaTilastotSplitPane.createAndShowGUI();
//            }
//        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                JFrame frame = new JFrame("Bodauslogi");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                try {
                    //Add content to the window.
                    frame.getContentPane().add(new PaaMenuTabbedPane());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage() + ex.getCause(), "Error", JOptionPane.WARNING_MESSAGE);
                    Logger.getLogger(Paaikkuna.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
