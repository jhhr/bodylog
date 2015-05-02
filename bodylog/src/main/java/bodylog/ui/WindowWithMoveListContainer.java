/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui;

import bodylog.logic.Move;
import javax.swing.JScrollPane;

public abstract class WindowWithMoveListContainer extends JScrollPane {

    protected MoveListContainer moveListContainer;
    protected MoveListContainerUpdater updater;

    public WindowWithMoveListContainer(MoveListContainerUpdater updater) {
        this.updater = updater;
    }

    /**
     * The action taken by the window when a move is selected in its
     * MoveListContainer.
     *
     * @param move the move that has been selected
     */
    public abstract void moveSelectedAction(Move move);

    /**
     * Returns the <code>MoveListContainer</code> contained in this window. Used
     * when the move list needs to be updated.
     *
     * @return the MoveListContainer of this window, a MoveChooser
     * @see bodylog.ui.edit.move.MoveEditor#saveToFile
     */
    public MoveListContainer getMoveListContainer() {
        return moveListContainer;
    }

    /**
     * Returns the <code>MoveListContainerUpdater</code> given to this window.
     * Used for updating the container list in the updater when a window is
     * closed.
     *
     * @return the MoveListContainerUpdater of this window, it is the same
     * object for all windows
     *
     * @see bodylog.ui.CloseableTab
     */
    public MoveListContainerUpdater getUpdater() {
        return updater;
    }

}
