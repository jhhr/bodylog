package bodylog.ui.edit;

import bodylog.logic.Move;
import bodylog.ui.edit.abstracts.WindowWithMoveChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Container for a JComboBox containing a list of Moves acquired from files.
 * Contained in a WindowWithMoveChooser. When a list item is clicked by the user
 * this asks its parent container to add an Editor for that item. The list is
 * contained in a <code>DefaultComboBoxModel</code> which uses the obsolete
 * collection <code>Vector</code> as its data model.
 */
public class MoveChooser extends JPanel implements ActionListener {

    private final WindowWithMoveChooser window;
    private final JComboBox moveList;

    /**
     * Creates a new MoveChooser for the specified WindowWithMoveChooser.
     *
     * @param window the WindowWithMoveChooser that contains this
     * @param model the data model initially given to the move list
     * @throws FileNotFoundException if a file is not found
     * @see bodylog.files.FromFile#allMovesWithoutSessions
     */
    public MoveChooser(WindowWithMoveChooser window, ComboBoxModel model) throws FileNotFoundException {
        this.window = window;
        this.moveList = new JComboBox();
        this.moveList.addActionListener(this);
        this.moveList.setModel(model);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Choose movement:"));
        add(moveList);
    }

    /**
     * Loads or reloads the list. Used when changes are made to move files.
     *
     * @param model ComboBoxModel to be set as the data model of the JComboBox
     * contained in the MoveChooser
     */
    public void updateMoveChooser(ComboBoxModel model) {
        moveList.setModel(model);
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
