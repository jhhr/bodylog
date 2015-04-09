package bodylog.ui;

import bodylog.ui.dataediting.MoveEditorWindow;
import bodylog.ui.dataviewing.StatWindow;
import bodylog.ui.dataediting.SessionEditorWindow;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

/**
 * Main UI window containing a menu bar from which tabs are opened.
 */
public class MainWindow extends JFrame implements ActionListener {

    private final JTabbedPane tabs;
    private final JMenuBar menuBar;
    private final JLabel info = new JLabel("Instructions");
    private static final String SESSION_EDITOR = "Session Editor";
    private static final String MOVE_EDITOR = "Move Editor";
    private static final String STATISTICS = "Statistics";
    private static final String INFO = "Help";

    public MainWindow() {
        super("Bodylog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        menuBar.add(mainMenu());
        menuBar.add(helpMenu());
        setJMenuBar(menuBar);

        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.add(INFO, info);
        tabs.setSelectedComponent(info);
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
                comp = info;
                break;
        }
        tabs.add(tabTitle, comp);
        tabs.setSelectedComponent(comp);
        tabs.setTabComponentAt(tabs.getTabCount() - 1, new CloseableTab(tabs));
    }

    public static void createAndShow() {

        final JFrame frame = new MainWindow();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger logger = Logger.getLogger("Uncaught Exception logger");
                FileHandler fileHandler;

                try {

                    // This block configure the logger with handler and formatter  
                    fileHandler = new FileHandler("ERROR.log");
                    logger.addHandler(fileHandler);
                    SimpleFormatter formatter = new SimpleFormatter();
                    fileHandler.setFormatter(formatter);

                    // Set to false to not print stacktrace to console
                    logger.setUseParentHandlers(true);

                    logger.info("Logger created.");

                } catch (SecurityException | IOException ex) {
                    logger.info("Exception encountered while adding file handler to logger.");
                }

                JOptionPane.showMessageDialog(null,
                        "An unexpected error occurred.\nProgram will close.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, null, e);
                frame.dispose();
            }
        });
        frame.setVisible(true);
    }

}
