
package bodylog.ui.dataediting;

import bodylog.logic.Move;
import bodylog.files.ToFile;


public class MoveEditor extends Editor{
    
    public MoveEditor(Move move, MoveEditorWindow window){
        super(move,window);
        
        add(closeButton("close movement"));
        setEditorBorder("");
    }
    
    private void createMove(){
        
    }

    @Override
    protected boolean fileExistsAlready() {
        return ToFile.moveFileExists(move);
    }

}
