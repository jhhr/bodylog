package bodylog.ui.view;

import bodylog.files.read.SessionReader;
import bodylog.ui.tables.view.ViewTable;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.exceptions.ParsingException;
import bodylog.logic.exceptions.VariableStateException;
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
 * using ViewTable as the TableModel.
 *
 * @see bodylog.ui.view.StatisticsViewerWindow
 * @see bodylog.ui.tables.view.ViewTable
 */
public class StatisticsDisplayer {

    private final HashMap<Move, JPanel> movesToDisplays;
    private final SessionReader reader;

    public StatisticsDisplayer() {
        movesToDisplays = new HashMap<>();
        reader = new SessionReader();
    }

    /**
     * Resets the display attached to the given Move. Next time when the display
     * for the Move is fetched, a new one will be created.
     *
     * @param move the Move for which to reset the display
     */
    public void resetDisplay(Move move) {
        movesToDisplays.put(move, null);
    }

    /**
     * Creates the tables for the given Move. Sessions are read from session
     * files and added to the Move. Each table consists of the data of one
     * Session.
     *
     * @param move move whose session data will be displayed
     * @return JPanel containing JTables displaying the data or an informative
     * JLabel if no sessions were found
     * @throws FileNotFoundException if a file cannot be found
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     */
    public JPanel getStatisticsDisplay(Move move) throws FileNotFoundException,
            ParsingException, VariableStateException {
        //get the display currently associated with this move
        JPanel statisticsPanel = movesToDisplays.get(move);
        //is null when fetched the first time or has been reset by updater
        if (statisticsPanel == null) {
            move.clearSessions();
            reader.fetchSessionsForMove(move);
            statisticsPanel = new StatisticsDisplay(move);
            movesToDisplays.put(move, statisticsPanel);
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
                    JTable table = new JTable(new ViewTable(session));
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
