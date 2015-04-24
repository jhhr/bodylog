
package bodylog.logic.exceptions;

import java.nio.file.FileSystemException;

/**
 * Custom exception for renaming files. Used when saving Moves.
 *
 * @see bodylog.files.edit.MoveSaver#saveToFile
 */
public class FileRenameException extends FileSystemException {

    public FileRenameException(String file, String other, String message) {
        super(file, other, message);
    }
}
