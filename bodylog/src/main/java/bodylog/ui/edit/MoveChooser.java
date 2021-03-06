package bodylog.ui.edit;

import bodylog.logic.Move;
import bodylog.ui.MoveListContainer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
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
     */
    public MoveChooser(WindowWithMoveChooser window, Move[] moves) {
        super(window);
        this.moveList = new JComboBox(new SortedComboBoxModel(moves));
        this.moveList.addActionListener(this);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Choose movement:"));
        add(moveList);
    }

    @Override
    public void reloadMoveList(Move[] moves) {
        moveList.setModel(new SortedComboBoxModel(moves));
    }

    @Override
    public void addMove(Move move) {
        //adding an item fires an event as if an item was selected
        //adding ActionListner avoids this
        moveList.removeActionListener(this);
        moveList.addItem(move);
        moveList.addActionListener(this);
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
     * @param e the event that triggers this method call: user clicks on a move
     * in the JComboBox
     *
     * @see bodylog.ui.edit.move.MoveEditorWindow#moveSelectedAction
     * @see bodylog.ui.edit.session.SessionEditorWindow#moveSelectedAction
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
