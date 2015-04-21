/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.logic.exceptions;

import java.nio.file.FileSystemException;

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
