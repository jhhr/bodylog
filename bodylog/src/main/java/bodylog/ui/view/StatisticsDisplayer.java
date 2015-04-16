package bodylog.ui.view;

import bodylog.files.read.SessionReader;
import bodylog.ui.tables.view.StatTable;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.ui.MoveListContainerUpdater;
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
 * in the session files of movements. The displays are sent to the
 * StatisticsViewerWindow. The display consists of JTables that contain the data
 * using StatTable as the TableModel.
 *
 * @see bodylog.ui.view.StatisticsViewerWindow
 * @see bodylog.ui.tables.view.StatTable
 */
public class StatisticsDisplayer {

    private final HashMap<Move, JPanel> movesToStatisticsMap;
    private final SessionReader reader;

    public StatisticsDisplayer() {
        movesToStatisticsMap = new HashMap<>();
        reader = new SessionReader();
    }
    
    public void resetDisplay(Move move){
        movesToStatisticsMap.put(move, null);
    }

    private Move fetchSessions(Move move) throws FileNotFoundException {
        return reader.addSessionsToMove(move);
    }

    /**
     * Creates the tables for the given Move. Sessions are read from session
     * files and added to the Move. Each table consists of the data of one
     * Session.
     *
     * @param move move whose session data will be displayed
     * @return JPanel containing JTables displaying the data or an informative
     * JLabel if no sessions were found
     * @throws FileNotFoundException if a session file cannot be found
     */
    public JPanel getStatisticsDisplay(Move move) throws FileNotFoundException {        
        JPanel statisticsPanel = movesToStatisticsMap.get(move);
        if (statisticsPanel == null) {
            move.clearSessions();
            statisticsPanel = newStatisticsDisplay(move);
            movesToStatisticsMap.put(move, statisticsPanel);
        }        
        return statisticsPanel;
    }
    
    private JPanel newStatisticsDisplay(Move move) throws FileNotFoundException{
        JPanel statisticsPanel = new JPanel();
        Move moveWithSessions = fetchSessions(move);       
        
        statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.Y_AXIS));

        ArrayList<Session> sessions = moveWithSessions.getSessions();
        if (sessions.isEmpty()) {
            statisticsPanel.add(new JLabel("Could not find any data to display for this movement"));
        } else {
            for (Session session : moveWithSessions.getSessions()) {
                JTable table = new JTable(new StatTable(session, moveWithSessions));
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
                statisticsPanel.add(pane);
            }
        }
        return statisticsPanel;
    }
}
