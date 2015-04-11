package bodylog.files;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A FileNameFilter that accepts files ending in the ending for move defining
 * files. Used in the file handling class FromFile.
 *
 * @see bodylog.files.Constant#MOVE_END
 * @see bodylog.files.FromFile#moveFileList
 */
public class MoveFileFilter implements FilenameFilter {

    /**
     * Checks if the file is a move file and is in the movements folder.
     *
     * @param dir the directory in which the file was found.
     * @param name the name of the file.
     * @return  <code>true</code> if the name ends in the move
     * file ending and is in the correct folder; <code>false</code> otherwise.
     * 
     * @see bodylog.files.Constant#MOVE_END
     * @see bodylog.files.Constant#MOVES_DIR
     */
    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(Constant.MOVE_END) && dir.equals(Constant.MOVES_DIR)) {
            return true;
        } else {
            return false;
        }
    }

}
