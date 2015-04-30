package bodylog.ui;

import bodylog.ui.help.HelpWindow;
import bodylog.ui.edit.move.MoveEditorWindow;
import bodylog.ui.view.StatisticsViewerWindow;
import bodylog.ui.edit.session.SessionEditorWindow;
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
 * <code>StatisticsViewerWindow</code> and <code>HelpWindow</code>.
 *
 * @see bodylog.ui.edit.move.MoveEditorWindow
 * @see bodylog.ui.edit.session.SessionEditorWindow
 * @see bodylog.ui.view.StatisticsViewerWindow
 * @see bodylog.ui.HelpWindow
 */
public class MainWindow extends JFrame implements ActionListener {

    private final JTabbedPane tabs;
    private final JMenuBar menuBar;
    private final MoveListContainerUpdater updater;
    
    private static final String SESSION_EDITOR = "Session Editor";
    private static final String MOVE_EDITOR = "Move Editor";
    private static final String STATISTICS = "Statistics";
    private static final String INFO = "Help";
    
    private static final String SESSION_ITEM_TITLE = "Add Sessions";
    private static final String MOVE_ITEM_TITLE = "Add/Edit Movements";
    private static final String STAT_ITEM_TITLE = "View Statistics";
    
    private static final String MAIN_MENU_TITLE = "Menu";
    private static final String HELP_MENU_TITLE = "Help";
    
    private static final String HELP_ITEM_TITLE = "View Help";

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

        this.updater = new MoveListContainerUpdater();

        menuBar = new JMenuBar();
        menuBar.add(mainMenu());
        menuBar.add(helpMenu());
        setJMenuBar(menuBar);

        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        addTab(INFO);

        setContentPane(tabs);
        setSize(new Dimension(600, 600));

        setLocationRelativeTo(null);
    }

    private JMenu mainMenu() {
        JMenu mainMenu = new JMenu(MAIN_MENU_TITLE);

        mainMenu.add(sessionEditMenuItem());
        mainMenu.add(moveEditMenuItem());        
        mainMenu.add(statisticsMenuItem());

        return mainMenu;
    }

    private JMenu helpMenu() {
        JMenu helpMenu = new JMenu(HELP_MENU_TITLE);
        helpMenu.add(infoMenuItem());

        return helpMenu;
    }

    private JMenuItem sessionEditMenuItem() {
        JMenuItem sessionItem = new JMenuItem(SESSION_ITEM_TITLE);
        sessionItem.setActionCommand(SESSION_EDITOR);
        sessionItem.addActionListener(this);
        return sessionItem;
    }

    private JMenuItem statisticsMenuItem() {
        JMenuItem statItem = new JMenuItem(STAT_ITEM_TITLE);
        statItem.setActionCommand(STATISTICS);
        statItem.addActionListener(this);
        return statItem;
    }

    private JMenuItem moveEditMenuItem() {
        JMenuItem moveItem = new JMenuItem(MOVE_ITEM_TITLE);
        moveItem.setActionCommand(MOVE_EDITOR);
        moveItem.addActionListener(this);
        return moveItem;
    }

    private JMenuItem infoMenuItem() {
        JMenuItem infoItem = new JMenuItem(HELP_ITEM_TITLE);
        infoItem.setActionCommand(INFO);
        infoItem.addActionListener(this);
        return infoItem;
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
        String itemTitle = e.getActionCommand();

        if (isTabOpen(itemTitle)) {
            return;
        }

        addTab(itemTitle);

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

    private void addTab(String tabTitle) {
        Component comp = null;
        try {
            switch (tabTitle) {
                case MOVE_EDITOR:
                    comp = new MoveEditorWindow(updater);
                    break;
                case SESSION_EDITOR:
                    comp = new SessionEditorWindow(updater);
                    break;
                case STATISTICS:
                    comp = new StatisticsViewerWindow(updater);
                    break;
                case INFO:
                    comp = new HelpWindow();
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        // adds a new tab for the new component
        tabs.add(tabTitle, comp);
        // sets the added tab as selected (changes active view to this component)
        tabs.setSelectedComponent(comp);
        // replace the tab with the custom tab that has a close button
        tabs.setTabComponentAt(tabs.getTabCount() - 1, new CloseableTab(tabs));
    }

}
