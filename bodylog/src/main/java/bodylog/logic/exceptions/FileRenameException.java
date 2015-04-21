/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.logic.exceptions;

import java.nio.file.FileSystemException;

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
