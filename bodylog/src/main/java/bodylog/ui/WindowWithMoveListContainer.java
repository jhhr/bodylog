/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui;

import bodylog.logic.Move;
import java.io.FileNotFoundException;
import javax.swing.JScrollPane;

public abstract class WindowWithMoveListContainer extends JScrollPane {

    protected MoveListContainer moveListContainer;
    protected MoveListContainerUpdater updater;
    
    public WindowWithMoveListContainer(MoveListContainerUpdater updater) 
            throws FileNotFoundException{
        this.updater = updater;
    }
    
    public abstract void moveSelectedAction(Move move);

    
    /**
     * Returns the <code>MoveListContainer</code> contained in this window. Used
     * when the move list needs to be updated.
     *
     * @return the MoveListContainer of this window, a MoveChooser
     * @see bodylog.ui.dataediting.MoveEditor#saveToFile
     */
    public MoveListContainer getListContainer() {
        return moveListContainer;
    }

    /**
     * Returns the MoveListContainerUpdater given to this window. Used for
     * updating the move list.
     *
     * @return the MoveListContainerUpdater of this window, it is the same
     * object for all windows
     */
    public MoveListContainerUpdater getUpdater() {
        return updater;
    }

}
