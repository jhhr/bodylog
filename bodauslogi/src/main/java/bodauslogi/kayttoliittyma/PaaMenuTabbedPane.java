package bodauslogi.kayttoliittyma;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class PaaMenuTabbedPane extends JPanel {

    public PaaMenuTabbedPane() throws Exception {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent datanLisaysPane = makeTextPanel("Panel #1");
        tabbedPane.addTab("Lisää dataa", datanLisaysPane);

        JComponent tilastotPane = new JScrollPane(new LiikelistaJaTilastotSplitPane());
        tabbedPane.addTab("Tarkastele dataa", tilastotPane);

        //Add the tabbed pane to this panel.
        add(tabbedPane);

//        tabbedPane.setPreferredSize(new Dimension(400, 200));

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            //Add content to the window.
            frame.add(new PaaMenuTabbedPane(), BorderLayout.CENTER);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage() + ex.getCause(), "Error", JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(PaaMenuTabbedPane.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                PaaMenuTabbedPane.createAndShowGUI();
            }
        });
    }
}
