package bodauslogi.util;

import java.io.File;
import java.io.FilenameFilter;

public class liikeFiltteri implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(Vakiot.LIIKEPAATE)) {
            return true;
        } else {
            return false;
        }
    }

}
