package bodylog.ui;

import bodylog.ui.dataediting.MoveEditorWindow;
import bodylog.ui.dataviewing.StatWindow;
import bodylog.ui.dataediting.SessionEditorWindow;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

/**
 * Main UI window containing a menu from which tabs are opened. Tabs are
 * closeable. Selecting to open a tab that is already open will not open another
 * one but make that tab be selected as active. Opens instances of
 * <code>MoveEditorWindow</code>, <code>SessionEditorWindow</code>, and
 * <code>StatWindow</code> and <code>HelpWindow</code>.
 *
 * @see bodylog.ui.dataediting.MoveEditorWindow
 * @see bodylog.ui.dataediting.SessionEditorWindow
 * @see bodylog.ui.dataviewing.StatWindow
 * @see bodylog.ui.HelpWindow
 */
public class MainWindow extends JFrame implements ActionListener {

    private final JTabbedPane tabs;
    private final JMenuBar menuBar;
    private static final String SESSION_EDITOR = "Session Editor";
    private static final String MOVE_EDITOR = "Move Editor";
    private static final String STATISTICS = "Statistics";
    private static final String INFO = "Help";

    /**
     * Creates a new MainWindow. Adds the menuBar, two menus and the
     * <code>JTabbedPane</code> which displays the other UI windows in tabs.
     * Opens the help window in a tab in the beginning so it is the first thing
     * shown to the user. Tabs use a custom tab button containing a button that
     * closes the tab.
     *
     * @see javax.swing.JTabbedPane
     * @see bodylog.ui.CloseableTab
     */
    public MainWindow() {
        super("Bodylog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        menuBar.add(mainMenu());
        menuBar.add(helpMenu());
        setJMenuBar(menuBar);

        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        HelpWindow info = new HelpWindow();
        tabs.add(INFO, info);
        tabs.setSelectedComponent(info);
        // sets the tab button as the custom closeable button
        tabs.setTabComponentAt(tabs.getTabCount() - 1, new CloseableTab(tabs));

        tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

        setContentPane(tabs);
        setSize(new Dimension(600, 600));

        setLocationRelativeTo(null);
    }

    private JMenu mainMenu() {
        JMenu mainMenu = new JMenu("Menu");

        mainMenu.add(sessionEditMenuItem());
        mainMenu.add(statisticsMenuItem());
        mainMenu.add(moveEditMenuItem());

        return mainMenu;
    }

    private JMenu helpMenu() {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(infoMenuItem());

        return helpMenu;
    }

    private JMenuItem sessionEditMenuItem() {
        JMenuItem sessionMenu = new JMenuItem("Add session(s)");
        sessionMenu.setActionCommand(SESSION_EDITOR);
        sessionMenu.addActionListener(this);
        return sessionMenu;
    }

    private JMenuItem statisticsMenuItem() {
        JMenuItem statMenu = new JMenuItem("View statistics");
        statMenu.setActionCommand(STATISTICS);
        statMenu.addActionListener(this);
        return statMenu;
    }

    private JMenuItem moveEditMenuItem() {
        JMenuItem moveMenu = new JMenuItem("Add/edit movements");
        moveMenu.setActionCommand(MOVE_EDITOR);
        moveMenu.addActionListener(this);
        return moveMenu;
    }

    private JMenuItem infoMenuItem() {
        JMenuItem infoMenu = new JMenuItem("Help");
        infoMenu.setActionCommand(INFO);
        infoMenu.addActionListener(this);
        return infoMenu;
    }

    /**
     * When the user clicks on a menu button a window of the corresponding kind
     * is opened unless that window has already been opened in which case is
     * simply made selected.
     *
     * @param e click event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        String itemTitle = menuItem.getActionCommand();

        if (isTabOpen(itemTitle)) {
            return;
        }

        try {
            addTab(itemTitle);
        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean isTabOpen(String tabTitle) {
        if (tabs.getComponentCount() > 0) {
            for (int i = 0; i < tabs.getTabCount(); i++) {
                if (tabs.getTitleAt(i).equals(tabTitle)) {
                    tabs.setSelectedIndex(i);
                    return true;
                }
            }
        }
        return false;
    }

    private void addTab(String tabTitle) throws Exception {
        Component comp = null;
        switch (tabTitle) {
            case MOVE_EDITOR:
                comp = new MoveEditorWindow();
                break;
            case SESSION_EDITOR:
                comp = new SessionEditorWindow();
                break;
            case STATISTICS:
                comp = new StatWindow();
                break;
            case INFO:
                comp = new HelpWindow();
                break;
        }
        // adds a new tab
        tabs.add(tabTitle, comp);
        // sets the added as selected (changes active tab to this one)
        tabs.setSelectedComponent(comp);
        // sets the tab button as the custom closeable button
        tabs.setTabComponentAt(tabs.getTabCount() - 1, new CloseableTab(tabs));
    }

}
