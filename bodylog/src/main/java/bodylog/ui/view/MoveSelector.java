package bodylog.ui.view;

import bodylog.logic.Move;
import bodylog.ui.MoveListContainer;
import bodylog.ui.WindowWithMoveListContainer;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Implementation of MoveListContainer used by StatisticsViewerWindow.
 *
 * @see bodylog.ui.view.SortedListModel
 * @see bodylog.ui.view.StatisticsViewerWindow
 */
public class MoveSelector extends MoveListContainer
        implements ListSelectionListener {

    private final JList moveList;
    private SortedListModel model;

    /**
     * Creates a new MoveSelector for the given window with the given Moves.
     * Uses a SortedListModel as the data model for the Move list.
     *
     * @param window the window this is contained in
     * @param moves the list of Moves this will use
     *
     * @see bodylog.ui.view.SortedListModel
     * @see bodylog.ui.view.StatisticsViewerWindow
     */
    public MoveSelector(WindowWithMoveListContainer window, Move[] moves) {
        super(window);
        this.model = new SortedListModel(moves);
        this.moveList = new JList(model);
        moveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moveList.setSelectedIndex(0);
        moveList.addListSelectionListener(this);
    }

    /**
     * Gets the JList contained in this.
     *
     * @return a JList
     */
    public JList getJList() {
        return moveList;
    }

    /**
     * Gets the Move currently selected in the list.
     *
     * @return a Move
     */
    public Move getSelectedMove() {
        return (Move) moveList.getSelectedValue();
    }

    @Override
    public void reloadMoveList(Move[] moves) {
        this.model = new SortedListModel(moves);
        moveList.setModel(model);
    }

    /**
     * Calls the <code>moveSelectedAction</code> method of the parent window.
     *
     * @param e the event that triggers this method call: user clicks on a Move
     * in the JList
     * 
     * @see bodylog.ui.view.StatisticsViewerWindow#moveSelectedAction
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        window.moveSelectedAction(getSelectedMove());
    }

    @Override
    public void addMove(Move move) {
        model.add(move);
    }

    @Override
    public void removeMove(Move move) {
        model.removeElement(move);
    }
}
