package bodylog.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A FileNameFilter that accepts files ending in the ending for session data
 * files, specified in {@link bodylog.util.Constant Constant}.
 *
 */
public class SessionFileFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(Constant.SESSION_END)) {
            return true;
        } else {
            return false;
        }
    }
}
