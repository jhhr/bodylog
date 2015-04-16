/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bodylog.ui;

import bodylog.logic.Move;
import javax.swing.JPanel;

public abstract class MoveListContainer extends JPanel{
    protected WindowWithMoveListContainer window;
    
    public MoveListContainer(WindowWithMoveListContainer window){
        this.window = window;
    }
    
    public abstract void reloadMoveList(Move[] moves);
    
    public abstract void addMove(Move move);
    
    public abstract void removeMove(Move move);
}
