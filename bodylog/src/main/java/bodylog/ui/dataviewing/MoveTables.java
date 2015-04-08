package bodylog.ui.dataviewing;

import bodylog.ui.tables.StatTable;
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
 * Class used to contain the session tables of each movement.
 *
 */
public class MoveTables extends JPanel {

    /**
     * Creates the tables for the given Move. The Move is supposed to contain
     * all the Sessions from which the the Sets will be displayed in table form.
     * Each table consists of the data of one Session. Uses
     * {@link bogylog.ui.tables.StatTable StatTable} as the TableModel.
     *
     * @param move the Move for which to display statistics
     */
    public MoveTables(Move move) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ArrayList<Session> sessions = move.getSessions();
        if (sessions.isEmpty()) {
            this.add(new JLabel("Could not data to display for this movement"));
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
                this.add(pane);
            }
        }
    }
}
