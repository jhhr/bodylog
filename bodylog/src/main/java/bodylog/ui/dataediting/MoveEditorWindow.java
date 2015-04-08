
package bodylog.ui.dataediting;

import bodylog.logic.Move;
import javax.swing.JButton;


public class MoveEditorWindow extends WindowWithMoveChooser{

    public MoveEditorWindow() throws Exception {
        super();
        
        JButton newMoveButton = new JButton("new movement");
        chooserPanel.add(newMoveButton);
    }

    @Override
    protected void addEditor(Move move) {
        editorPanel.add(new MoveEditor(move,this));
        validate();
        repaint();
    }

    @Override
    protected boolean updateAllowed(Move liike) {
        return moveHasOpenEditor(liike, "editor");
    }

}
