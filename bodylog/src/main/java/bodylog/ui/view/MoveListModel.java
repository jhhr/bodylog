
package bodylog.ui.view;

import bodylog.logic.Move;
import javax.swing.DefaultListModel;

/**
 * Implementation of ListModel using an array of Moves used by the MoveJList.
 * 
 * @see bodylog.ui.view.MoveJList
 */
public class MoveListModel extends DefaultListModel<Move> {

    private final Move[] moves;

    public MoveListModel(Move[] moves) {
        this.moves = moves;
    }

    @Override
    public int getSize() {
        return moves.length;
    }

    @Override
    public Move getElementAt(int index) {
        return moves[index];
    }
    
    public void addMove(Move move){
        addElement(move);
    }
    
    public void removeMove(Move move){
        removeElement(move);
    }
}
