package bodylog.ui.view;

import bodylog.ui.tables.view.StatTable;
import bodylog.logic.Move;
import bodylog.logic.Session;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * The UI component responsible for displaying the data contained in the session
 * files. Contained in a StatWindow, as one half of the SplitPane. Contains
 * JTables that contain the data using StatTable as the TableModel.
 *
 * @see bodylog.ui.view.StatWindow
 * @see bodylog.ui.tables.view.StatTable
 */
public class MoveTables extends JPanel {

    /**
     * Creates the tables for the given Move. The Move is supposed to contain
     * all the Sessions from which the tables are made. Each table consists of
     * the data of one Session.
     *
     * @param move the Move for which to display statistics
     */
    public MoveTables(Move move) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ArrayList<Session> sessions = move.getSessions();
        if (sessions.isEmpty()) {
            add(new JLabel("Could not find any data to display for this movement"));
        } else {
            for (Session session : move.getSessions()) {
                JTable table = new JTable(new StatTable(session, move));
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
