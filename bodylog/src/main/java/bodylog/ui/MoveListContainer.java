/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui;

import bodylog.logic.Move;
import javax.swing.JPanel;

public abstract class MoveListContainer extends JPanel {

    protected WindowWithMoveListContainer window;

    public MoveListContainer(WindowWithMoveListContainer window) {
        this.window = window;
    }

    /**
     * Makes the data model holding the list of Moves.Used when changes are made
     * to move files.
     *
     * @param moves the list of Moves the data model will be based on
     */
    public abstract void reloadMoveList(Move[] moves);

    /**
     * Adds the specified Move from the list.
     *
     * @param move the Move to be added
     */
    public abstract void addMove(Move move);

    /**
     * Removes the specified Move from the list.
     *
     * @param move the Move to be removed
     */
    public abstract void removeMove(Move move);
}
