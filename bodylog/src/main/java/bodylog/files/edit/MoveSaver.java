/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.files.edit;

import bodylog.files.Saver;
import bodylog.files.Constant;
import bodylog.logic.Move;
import bodylog.ui.MoveListContainerUpdater;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;

/**
 * Class for encapsulating the saving of a <code>Move</code> to file and editing
 * files relating to a <code>Move</code>.
 */
public class MoveSaver extends Saver {

    private final String oldName;

    private static final int SUCCESS = 0;
    private static final int FILE_RENAME_FAILED = 1;
    private static final int FOLDER_RENAME_FAILED = 2;
    private static final int BOTH_RENAMES_FAILED = 3;

    private static final int NEW_MOVE = 4;
    private static final int MOVE_UPDATED = 5;
    private static final int MOVE_RENAMED = 6;

    /**
     * Creates a new MoveSaver for the specified Move.
     *
     * @param updater MoveListContainerUpdater used to inform MoveListContainers
     * that a Move has been edited or created
     * @param move Move to be saved
     * @see bodylog.logic.Move
     */
    public MoveSaver(MoveListContainerUpdater updater, Move move) {
        //this will be an empty string if the Move was not loaded from file
        super(updater,move);
        this.oldName = move.getName();
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
     * file, checks if the name was changed and renames move files and folders.
     * Writes one line per each variable. Nothing is written if no variables are
     * found. After successful saving, informs the MoveListContainerUpdater of
     * the made changes.
     *
     * @throws IOException If the file cannot be created or opened or if an
     * error occurs during writing. Probably a problem outside this program.
     * @throws FileRenameException If the file to be renamed doesn't exist (user
     * deleted it), the file and rename target both exist (user set Move name
     * same as one that is saved) or renaming failed for unknown reasons.
     * @throws FileCreationException If the creation of the move folder fails
     * for unknown reasons.
     * @throws IllegalArgumentException If the name of the Move is blank. User
     * error, trying to save a new Move without changing name.
     * @see bodylog.logic.Move
     * @see bodylog.ui.dataediting.MoveEditor
     * @see bodylog.files.Constant#MOVES_DIR
     * @see bodylog.files.Constant#MOVE_END
     */
    @Override
    public void saveToFile() throws SecurityException, IllegalArgumentException,
            FileCreationException, FileRenameException, IOException {
        int updateStatus = 0;
        if (move.getName().isEmpty()) {//is the name blank?
            throw new IllegalArgumentException(
                    "Saving a move with a blank name is not allowed.");
        } else if (oldName.isEmpty()) {//is this a new Move?
            if (fileExists()) {//has the name already been used?
                throw new IllegalArgumentException(
                        "This name has already been used by an existing movement.");
            }
            createSessionFolder();
            updateStatus = NEW_MOVE;
        } else if (!oldName.equals(move.getName())) {//was the name changed?
            switch (renameFiles()) {
                //response to results of file renaming
                case SUCCESS:
                    updateStatus = MOVE_RENAMED;
                    break;
                case FILE_RENAME_FAILED:
                    throw new FileRenameException(oldName, move.getName(),
                            "Move file renaming failed for unknown reasons. "
                            + "Folder renaming not attempted. "
                            + "Try saving again or fix this manually.");
                case FOLDER_RENAME_FAILED:
                    throw new FileRenameException(oldName, move.getName(),
                            "Move file rename succeeded but "
                            + "Move folder renaming failed for unknown reasons."
                            + "You need to manually fix this.");
                case BOTH_RENAMES_FAILED:
                    throw new FileRenameException(oldName, move.getName(),
                            "Both Move file and folder renaming failed "
                            + "for unknown reasons. "
                            + "Try saving again or fix this manually.");
            }
        } else {//the Move was edited but name wasn't changed
            updateStatus = MOVE_UPDATED;
        }
        /**
         * Why rename instead of deleting if it's just going to be overwritten?
         * Because an error might occur here and the previous data would be
         * lost.
         */
        writeToFile();
        informUpdater(updateStatus);
    }

    /**
     * Creates the session folder for this movement. The name of the folder is
     * the name of the <code>Move</code>. Return false only when the operation
     * fails and the file does not exist afterward.
     *
     * @throws SecurityException if any of the files cannot be accessed
     */
    private boolean createSessionFolder() throws SecurityException,
            FileCreationException {
        File sesFolder = new File(Constant.DATA_DIR, move.getName());
        sesFolder.mkdirs();
        if (!sesFolder.exists()) {
            throw new FileCreationException(sesFolder.getPath(),
                    "Movement folder creation for '" + move + "' failed"
                    + "for unknown reasons.");
        } else {
            return true;
        }
    }

    /**
     * Renames the specified file to the target.
     */
    private int renameFiles() throws SecurityException, FileSystemException {        
        File oldFile = new File(Constant.MOVES_DIR, oldName + Constant.MOVE_END);
        File newFile = new File(Constant.MOVES_DIR, move.getName() + Constant.MOVE_END);
        
        //throw exception if renaming would fail with move files
        throwRenameException(oldFile, newFile, "file");    
        File oldFolder = new File(Constant.DATA_DIR, oldName);
        File newFolder = new File(Constant.DATA_DIR, move.getName());
        
        //throw exception if renaming would fail with move folders
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
            throws SecurityException, FileSystemException,
            FileAlreadyExistsException {

        if (!file.exists()) {//does file exist?
            //if not, renaming would fail, instead throw an exception
            throw new FileRenameException(file.getPath(), null,
                    "Cannot rename the movement " + fileType + " '"
                    + file.getName() + "'." + " It doesn't exist!");
        } else if (target.exists()) {//do both file and target exist?
            //if so, renaming would fail, instead throw an exception
            throw new FileRenameException(file.getPath(), target.getPath(),
                    "Cannot rename the movement " + fileType + " '"
                    + file.getName() + "'." + " The target " + fileType + "'"
                    + target.getName() + "' already exists!");
        }//file exists but target doesn't, renaming should succeed
    }

    private void writeToFile() throws IOException {
        File moveFile = new File(Constant.MOVES_DIR, move.getName() + Constant.MOVE_END);
        FileWriter writer = new FileWriter(moveFile);
        for (String variable : move.getVariables()) {
            writer.write(variable + "\n");
        }
        writer.close();
    }

    private void informUpdater(int updateStatus) {
        switch (updateStatus) {
            case NEW_MOVE:
                updater.updateContainersWithNewMove(move);
                break;
            case MOVE_UPDATED:
            case MOVE_RENAMED:
                updater.updateContainersWithChangedMove(new Move(oldName), move);
        }
    }

}
