/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.files;

import bodylog.logic.Move;
import bodylog.ui.MoveListContainerUpdater;
import java.nio.file.FileSystemException;

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
}
