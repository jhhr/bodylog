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
    
    
        /**
     * Custom exception used for creating files or folders.
     * 
     * @see bodylog.files.edit.MoveSaver#createSessionFolder
     */
    public class FileCreationException extends FileSystemException {

        public FileCreationException(String file, String message) {
            super(file, null, message);
        }
    }

    /**
     * Custom exception for renaming files.
     * 
     * @see bodylog.files.edit.MoveSaver#saveToFile
     */
    public class FileRenameException extends FileSystemException {

        public FileRenameException(String file, String other, String message) {
            super(file, other, message);
        }
    }

}
