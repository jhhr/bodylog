package bodylog.logic.exceptions;

import java.nio.file.FileSystemException;

/**
 * Custom exception used for creating files or folders.
 *
 * @see bodylog.files.write.MoveSaver#createSessionFolder
 */
public class FileCreationException extends FileSystemException {

    public FileCreationException(String file, String message) {
        super(file, null, message);
    }
}
