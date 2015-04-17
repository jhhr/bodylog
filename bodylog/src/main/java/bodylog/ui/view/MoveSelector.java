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

    public MoveSelector(WindowWithMoveListContainer window, Move[] moves) {
        super(window);
        this.model = new SortedListModel(moves);
        this.moveList = new JList(model);
        moveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moveList.setSelectedIndex(0);
        moveList.addListSelectionListener(this);
    }

    public JList getJList() {
        return moveList;
    }

    public Move getSelectedMove() {
        return (Move) moveList.getSelectedValue();
    }

    @Override
    public void reloadMoveList(Move[] moves) {
        this.model = new SortedListModel(moves);
        moveList.setModel(model);
    }

    //Listens to the list
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        Move move = (Move) list.getSelectedValue();
        window.moveSelectedAction(move);
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
