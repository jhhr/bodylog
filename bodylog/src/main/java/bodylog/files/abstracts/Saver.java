
package bodylog.files.abstracts;

import bodylog.logic.Move;
import bodylog.ui.MoveListContainerUpdater;

public abstract class Saver {

    protected final MoveListContainerUpdater updater;
    protected final Move move;

    public Saver(MoveListContainerUpdater updater, Move move) {
        this.updater = updater;
        this.move = move;
    }

    public abstract void saveToFile() throws Exception;

    public abstract boolean fileExists();

    public Move getMove() {
        return move;
    }

//    public MoveListContainerUpdater getUpdater() {
//        return updater;
//    }
}
