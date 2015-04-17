package bodylog.ui.view;

import bodylog.files.read.SessionReader;
import bodylog.ui.tables.view.ViewTable;
import bodylog.logic.Move;
import bodylog.logic.Session;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * The UI component responsible for generating a display of the data contained
 * in the fetchSession files of movements. The displays are sent to the
 * StatisticsViewerWindow. The display consists of JTables that contain the data
 * using ViewTable as the TableModel.
 *
 * @see bodylog.ui.view.StatisticsViewerWindow
 * @see bodylog.ui.tables.view.ViewTable
 */
public class StatisticsDisplayer {

    private final HashMap<Move, JPanel> movesToStatisticsMap;
    private final SessionReader reader;

    public StatisticsDisplayer() {
        movesToStatisticsMap = new HashMap<>();
        reader = new SessionReader();
    }

    public void resetDisplay(Move move) {
        movesToStatisticsMap.put(move, null);
    }

    private Move fetchSessions(Move move) throws FileNotFoundException {
        return reader.fetchSessionsForMove(move);
    }

    /**
     * Creates the tables for the given Move. Sessions are read from
     * fetchSession files and added to the Move. Each table consists of the data
     * of one Session.
     *
     * @param move move whose session data will be displayed
     * @return JPanel containing JTables displaying the data or an informative
     * JLabel if no sessions were found
     * @throws FileNotFoundException if a file cannot be found
     */
    public JPanel getStatisticsDisplay(Move move) throws FileNotFoundException {
        JPanel statisticsPanel = movesToStatisticsMap.get(move);
        if (statisticsPanel == null) {
            move.clearSessions();
            statisticsPanel = new StatisticsDisplay(fetchSessions(move));
            movesToStatisticsMap.put(move, statisticsPanel);
        }
        return statisticsPanel;
    }

    private class StatisticsDisplay extends JPanel {

        public StatisticsDisplay(Move move) {

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            ArrayList<Session> sessions = move.getSessions();
            if (sessions.isEmpty()) {
                add(new JLabel("Could not find any data to display for this movement"));
            } else {
                for (Session session : move.getSessions()) {
                    JTable table = new JTable(new ViewTable(session, move));
                    table.setPreferredScrollableViewportSize(table.getPreferredSize());
                    table.setFillsViewportHeight(true);
                    JScrollPane pane = new JScrollPane(table);

                    String dateStr = session.getUIDateString();

                    pane.setBorder(
                            BorderFactory
                            .createCompoundBorder(
                                    BorderFactory.createTitledBorder(dateStr),
                                    BorderFactory.createEmptyBorder(5, 5, 5, 5))
                    );
                    add(pane);
                }
            }
        }
    }
}
