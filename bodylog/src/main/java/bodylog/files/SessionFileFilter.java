package bodylog.files;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A FileNameFilter that accepts files ending in the ending for session data
 * files. Used in the file handling class FromFile.
 *
 * @see Constant#SESSION_END
 * @see FromFile#moveWithSessions
 */
public class SessionFileFilter implements FilenameFilter {

    /**
     * Checks if the file is a session file. Ignores the parent directory as it is
     * named by the Move and can be named anything.
     *
     * @param dir the directory in which the file was found.
     * @param name the name of the file.
     * @return  <code>true</code> if and only if the name ends in the session
     * file ending; <code>false</code> otherwise.
     */
    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(Constant.SESSION_END)) {
            return true;
        } else {
            return false;
        }
    }
}
