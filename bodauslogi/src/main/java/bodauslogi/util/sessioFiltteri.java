package bodauslogi.util;

import java.io.File;
import java.io.FilenameFilter;

public class sessioFiltteri implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(Vakiot.SESSIOPAATE)) {
            return true;
        } else {
            return false;
        }
    }
}
