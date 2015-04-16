package bodylog.ui.edit;

import bodylog.logic.Move;
import bodylog.ui.MoveListContainer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * Container for a JComboBox containing a list of Moves acquired from files.
 * Contained in a WindowWithMoveChooser. When a list item is clicked by the user
 * this asks its parent container to add an Editor for that item. The list is
 * contained in a <code>DefaultComboBoxModel</code> which uses the obsolete
 * collection <code>Vector</code> as its data model.
 */
public class MoveChooser extends MoveListContainer
        implements ActionListener {

    private final JComboBox moveList;

    /**
     * Creates a new MoveChooser for the specified WindowWithMoveChooser.
     *
     * @param window the WindowWithMoveChooser that contains this
     * @param moves the list of Moves initially used to populate the JComboBox
     * @throws FileNotFoundException if a file is not found
     * @see bodylog.files.FromFile#allMovesWithoutSessions
     */
    public MoveChooser(WindowWithMoveChooser window, Move[] moves)
            throws FileNotFoundException {
        super(window);
        this.moveList = new JComboBox(moves);
        this.moveList.addActionListener(this);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Choose movement:"));
        add(moveList);
    }

    /**
     * Loads or reloads the list. Used when changes are made to move files.
     *
     * @param moves list of Moves to be used in creating a new
     * DefaultComboBoxModel for MoveChooser
     */
    @Override
    public void reloadMoveList(Move[] moves) {
        moveList.setModel(new DefaultComboBoxModel(moves));
    }

    @Override
    public void addMove(Move move) {
        moveList.addItem(move);
    }

    @Override
    public void removeMove(Move move) {
        //removing an item fires an event as if an item was selected
        //removing ActionListner avoids this
        moveList.removeActionListener(this);
        moveList.removeItem(move);
        moveList.addActionListener(this);
    }

    /**
     * Calls <code>moveSelectedAction</code> of the parent window
     *
     * @param e User clicks on a move in the ComboBox
     * @see bodylog.ui.dataediting.WindowWithMoveChooser#addNewEditor
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        Move move = (Move) cb.getSelectedItem();
        try {
            window.moveSelectedAction(move);
        } catch (Exception ex) {
            throw ex;
        }
    }

}
