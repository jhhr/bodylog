/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.files.edit;

import bodylog.files.Constant;
import bodylog.logic.Move;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

/**
 * Class for encapsulating the saving of a <code>Move</code> to file and editing
 * files relating to a <code>Move</code>.
 */
public class MoveSaver implements Saver {

    private final Move move;
    private final String oldName;

    private static final int FILE_FAIL = 1;
    private static final int FOLDER_FAIL = 2;
    private static final int NO_FAIL = 0;
    private static final int BOTH_FAIL = 3;

    /**
     * Creates a new MoveSaver for the specified Move.
     *
     * @param move Move to be saved
     * @see bodylog.logic.Move
     */
    public MoveSaver(Move move) {
        this.move = move;
        //this will be an empty string if the Move was not loaded from file
        this.oldName = move.getName();
    }

    @Override
    public Move getMove() {
        return move;
    }

    /**
     * Checks if this <code>Move</code> has a move file in the movements folder.
     * The move file has the same name as the Move with added move file specific
     * ending.
     *
     * @return true if the file exists, false otherwise
     * @see bodylog.ui.dataediting.MoveEditor
     * @see bodylog.files.Constant#MOVES_DIR
     * @see bodylog.files.Constant#MOVE_END
     */
    @Override
    public boolean fileExists() {
        return new File(Constant.MOVES_DIR, move.getName() + Constant.MOVE_END).exists();
    }

    /**
     * Writes the variables and name of the Move of this MoveSaver to file.
     * Doesn't allow a blank-named Move to be saved. If the Move was loaded from
     * file, checks if the name was changed and renaming of files is required.
     * Checks if the file Writes one line per each variable. Nothing is written
     * if no variables are found.
     *
     * @throws IOException If the file cannot be created or opened or if an
     * error occurs during writing. Probably a problem outside this program.
     * @throws java.io.FileNotFoundException If the file to be renamed doesn't
     * exist. Probably user error; for example, they manually deleted the move
     * file or folder while editing and then changed the name and tried to save.
     * @throws SecurityException If the files cannot be accessed during
     * renaming. Probably a problem outside this program.
     * @throws FileAlreadyExistsException If the rename target file already
     * exists. User error; renaming a Move to the same name as one previously
     * saved and then trying to save.
     * @throws IllegalArgumentException If the name of the Move is blank. User
     * error, trying to save a new Move without changing name.
     * @see bodylog.logic.Move
     * @see bodylog.ui.dataediting.MoveEditor
     * @see bodylog.files.Constant#MOVES_DIR
     * @see bodylog.files.Constant#MOVE_END
     */
    @Override
    public void saveToFile() throws IOException, FileNotFoundException,
            SecurityException, IllegalArgumentException, FileAlreadyExistsException {
        //first check if name is blank
        if (move.getName().isEmpty()) {
            throw new IllegalArgumentException(
                    "Saving a move with a blank name is not allowed.");

        }// if not, check if this is a new Move
        else if (oldName.isEmpty()) {
            if(!createSessionFolder()){
                //response if folder doesn't exist despite trying to create it
            }
        } //if not, check if the name has been changed
        else if (!oldName.equals(move.getName())) {
            switch (renameFiles()) {
                //response to results of file renaming
                case NO_FAIL:
                    ;
                case FILE_FAIL:
                    ;
                case FOLDER_FAIL:
                    ;
                case BOTH_FAIL:
                    ;
            }
        }
        /**
         * Why rename instead of deleting if it's just going to be overwritten?
         * Because an error might occur here and the previous data would be
         * lost.
         */
        writeToFile();
    }

    /**
     * Creates the session folder for this movement. The name of the folder is
     * the name of the <code>Move</code>. Return false only when the operation
     * fails and the file does not exist afterward.
     */
    private boolean createSessionFolder() throws SecurityException {
        File sesFolder = new File(Constant.DATA_DIR, move.getName());
        return !sesFolder.exists() ? sesFolder.mkdirs() : true;
    }

    /**
     * Renames the specified file to the target.
     */
    private int renameFiles()
            throws SecurityException, FileNotFoundException,
            FileAlreadyExistsException {
        //check both sets of files, first the move files
        File oldFile = new File(Constant.MOVES_DIR, oldName + Constant.MOVE_END);
        File newFile = new File(Constant.MOVES_DIR, move.getName() + Constant.MOVE_END);
        //throw exception if renaming would fail
        throwRenameException(oldFile, newFile, "file");
        //then the session folders
        File oldFolder = new File(Constant.DATA_DIR, oldName);
        File newFolder = new File(Constant.DATA_DIR, move.getName());
        //throw exception if renaming would fail
        throwRenameException(oldFolder, newFolder, "folder");
        /**
         * Now the only way to fail is by not being able to access the file
         * (throws a SecurityException) or some other unexpected way. Returning
         * false will signal this unexpected failure having happened.
         */
        int returnValue = 0;
        //add 0 if rename successful, 1 if not
        returnValue += oldFile.renameTo(newFile) ? 0 : 1;
        //add 0 if successful, 2 if not
        returnValue += oldFolder.renameTo(newFolder) ? 0 : 2;
        //result: 1 if file rename failed, 2 if folder, 3 if both, 0 if neither
        return returnValue;
    }

    private void throwRenameException(File file, File target, String fileType)
            throws SecurityException, FileNotFoundException,
            FileAlreadyExistsException {

        String newName = move.getName();
        if (!file.exists()) {//check if file to be renamed exists
            //if not, renaming would fail, instead throw an exception
            throw new FileNotFoundException(
                    "Cannot rename the movement " + fileType + " '" + oldName + "'."
                    + " It doesn't exist!");
        } else if (target.exists()) {// if so, check if the target also exists
            //if not, renaming would fail, instead throw an exception
            throw new FileAlreadyExistsException(newName, oldName,
                    "Cannot rename the movement " + fileType + " '" + oldName + "'."
                    + " The target " + fileType + "'" + newName + "' already exists!");
        }
    }

    private void writeToFile() throws IOException {
        File moveFile = new File(Constant.MOVES_DIR, move.getName() + Constant.MOVE_END);
        FileWriter writer = new FileWriter(moveFile);
        for (String variable : move.getVariables()) {
            writer.write(variable + "\n");
        }

        writer.close();
    }

}
