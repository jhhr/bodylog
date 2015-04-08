package bodylog.ui;

import bodylog.ui.dataediting.MoveEditorWindow;
import bodylog.ui.dataviewing.StatWindow;
import bodylog.ui.dataediting.SessionEditorWindow;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class MainWindow extends JFrame {

    private JTabbedPane tabs;
    private JMenuBar menuBar;
    private JLabel info = new JLabel("Instructions");
    private static final String SESSION_EDITOR_TABTITLE = "Session Editor";
    private static final String MOVE_EDITOR_TABTITLE = "Move Editor";
    private static final String STATISTIC_VIEWER_TABTITLE = "Statistics";
    private static final String INFO_TABTITLE = "Help";

    public MainWindow(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        JMenu menu = new JMenu("Menu");

        JMenuItem sessionMenu = new JMenuItem("Add session(s)");
        sessionMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabs.getComponentCount() > 0) {
                    for (int i = 0; i < tabs.getTabCount(); i++) {
                        if (tabs.getTitleAt(i).equals(SESSION_EDITOR_TABTITLE)) {
                            tabs.setSelectedIndex(i);
                            return;
                        }
                    }
                }
                try {
                    SessionEditorWindow sesEdit = new SessionEditorWindow();
                    tabs.add(SESSION_EDITOR_TABTITLE, sesEdit);
                    tabs.setSelectedComponent(sesEdit);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                tabs.setTabComponentAt(tabs.getTabCount() - 1, new CloseableTab(tabs));
            }
        });
        menu.add(sessionMenu);

        JMenuItem statMenu = new JMenuItem("View statistics");
        statMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabs.getComponentCount() > 0) {
                    for (int i = 0; i < tabs.getTabCount(); i++) {
                        if (tabs.getTitleAt(i).equals(STATISTIC_VIEWER_TABTITLE)) {
                            tabs.setSelectedIndex(i);
                            return;
                        }
                    }
                }
                try {
                    JScrollPane statView = new JScrollPane(new StatWindow());
                    tabs.add(STATISTIC_VIEWER_TABTITLE, statView);
                    tabs.setSelectedComponent(statView);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                tabs.setTabComponentAt(tabs.getTabCount() - 1, new CloseableTab(tabs));
            }
        });
        menu.add(statMenu);

        JMenuItem moveMenu = new JMenuItem("Add/edit movements");
        moveMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabs.getComponentCount() > 0) {
                    for (int i = 0; i < tabs.getTabCount(); i++) {
                        if (tabs.getTitleAt(i).equals(MOVE_EDITOR_TABTITLE)) {
                            tabs.setSelectedIndex(i);
                            return;
                        }
                    }
                }

                try {
                    MoveEditorWindow liikkeet = new MoveEditorWindow();
                    tabs.add(MOVE_EDITOR_TABTITLE, liikkeet);
                    tabs.setSelectedComponent(liikkeet);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

                tabs.setTabComponentAt(tabs.getTabCount() - 1, new CloseableTab(tabs));
            }
        });
        menu.add(moveMenu);

        JMenuItem infoMenu = new JMenuItem("Help");
        infoMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabs.getComponentCount() > 0) {
                    for (int i = 0; i < tabs.getTabCount(); i++) {
                        if (tabs.getTitleAt(i).equals(INFO_TABTITLE)) {
                            tabs.setSelectedIndex(i);
                            return;
                        }
                    }
                }
                tabs.add(INFO_TABTITLE, info);
                tabs.setSelectedComponent(info);
                tabs.setTabComponentAt(tabs.getTabCount() - 1, new CloseableTab(tabs));
            }
        });
        menu.add(infoMenu);

        tabs.add("Ohjeet", info);
        tabs.setSelectedComponent(info);
        tabs.setTabComponentAt(tabs.getTabCount() - 1, new CloseableTab(tabs));

        menuBar.add(menu);
        setJMenuBar(menuBar);
        tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        setContentPane(tabs);
        setSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
    }

    public static void createAndShowWindow() {

        JFrame frame = new MainWindow("Bodauslogi");

        frame.setVisible(true);
    }

}
