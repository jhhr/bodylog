package bodylog.ui.dataediting;

import bodylog.ui.dataediting.abstracts.WindowWithMoveChooser;
import bodylog.logic.Move;
import bodylog.files.FromFile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Container for a ComboBox containing a list of Moves acquired form move files.
 * Contained in a <code>WindowWithMoveChooser</code>. When a move is clicked by
 * the user this asks its parent container to add an Editor. The list is
 * contained in a <code>JComboBox</code>, of really in its
 * <code>DefaultComboBoxModel</code> which uses the obsolete collection
 * <code>Vector</code> in its as the data model.
 */
public final class MoveChooser extends JPanel implements ActionListener {

    private WindowWithMoveChooser window;
    private JComboBox moveList;

    /**
     * Creates a new MoveChooser for the specified WindowWithMoveChooser. The
     * ComboBox is populated by reading the move files with
     * <code>FromFile.allMovesWithoutSessions</code>.
     *
     * @param window the WindowWithMoveChooser that contains this
     * @throws Exception if something happens in reading the move files
     * @see bodylog.files.FromFile#allMovesWithoutSessions
     */
    public MoveChooser(WindowWithMoveChooser window) throws Exception {
        this.window = window;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Choose movement:"));

        loadMoveList();
        add(moveList);
    }

    /**
     * Loads or reloads the list. Used when changes are made to move files.
     * 
     * @throws Exception when file reading fails
     */
    public void loadMoveList() throws Exception {
        moveList = new JComboBox(FromFile.allMovesWithoutSessions());
        moveList.addActionListener(this);
    }

    /**
     * Calls <code>addNewEditor</code> of the parent window
     *
     * @param e User clicks on a move in the ComboBox
     * @see bodylog.ui.dataediting.WindowWithMoveChooser#addNewEditor
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        Move move = (Move) cb.getSelectedItem();
        try {
            window.addNewEditor(move);
        } catch (Exception ex) {
            throw ex;
        }
    }

}
