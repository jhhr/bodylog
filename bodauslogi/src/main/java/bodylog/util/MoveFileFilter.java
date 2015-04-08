package bodylog.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A FileNameFilter that accepts files ending in the ending for move defining
 * files, specified in {@link bogylog.util.Constant Constant}.
 *
 */
public class MoveFileFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(Constant.MOVE_END)) {
            return true;
        } else {
            return false;
        }
    }

}
