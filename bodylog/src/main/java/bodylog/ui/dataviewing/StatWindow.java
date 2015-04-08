package bodylog.ui.dataviewing;

import bodylog.logic.Move;
import bodylog.files.FromFile;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The window in which statistics for a movement will be shown. Contains a
 * JSplitPane with the list of Moves and the
 * {@link bodylog.ui.dataviewing.MoveTables MoveTables}.
 *
 */
public class StatWindow extends JPanel implements ListSelectionListener {

    private final JPanel tablePanel;
    private final JList list;
    private final JSplitPane splitPane;
    private final String[] moveNameList;
    private final HashMap<String, JPanel> namesToTablesMap;

    /**
     * Constructs a new statistics displaying window. All moves are read from
     * the move folder. Session data is read from the data folder for each move
     * and a {@link bodylog.ui.dataviewing.MoveTables MoveTables} is created
     * from that. Each move name is linked to its respective MoveTables in a
     * HashMap. When the list is clicked the statistics window's content is
     * changed to the chosen Move's MoveTables.
     *
     * @throws Exception when things go wrong reading from file
     */
    public StatWindow() throws Exception {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Move[] moveArray = FromFile.allMovesWithSessions();

        moveNameList = new String[moveArray.length];
        namesToTablesMap = new HashMap<>();

        for (int i = 0; i < moveArray.length; i++) {
            Move move = moveArray[i];
            String moveName = move.toString();
            moveNameList[i] = moveName;
            namesToTablesMap.put(moveName, new MoveTables(move));
        }

        list = new JList(moveNameList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

        JScrollPane listScrollPane = new JScrollPane(list);
        tablePanel = new JPanel();

        JScrollPane tablesScrollPane = new JScrollPane(tablePanel);

        //Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, tablesScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(100);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(50, 50);
        listScrollPane.setMinimumSize(minimumSize);
        tablesScrollPane.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 400));
        this.add(splitPane);

        if (moveArray.length == 0) {
            tablePanel.add(new JLabel("Could not find any movements from\n"
                    + "which to display statistics"));
        } else {
            updateTables(moveNameList[list.getSelectedIndex()]);
        }
    }

    //Listens to the list
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        updateTables(moveNameList[list.getSelectedIndex()]);
    }

    protected void updateTables(String moveName) {
        for (Component oldTables : tablePanel.getComponents()) {
            tablePanel.remove(oldTables);
        }
        tablePanel.add(namesToTablesMap.get(moveName));
        tablePanel.validate();
        tablePanel.repaint();
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }
}
