package bodylog.files;

import java.io.File;

public class Util {

    /**
     * Deletes all files in folders that are in the data folder, deletes those
     * folders and the data folder. Deletes all files in the move folder and
     * deletes the move folder.
     */
    public static void deleteFiles() {

        if (Constant.DATA_DIR.exists()) {
            for (File folder : Constant.DATA_DIR.listFiles()) {
                if (folder.isDirectory()) {
                    for (File file : folder.listFiles()) {
                        file.delete();
                    }
                }
                folder.delete();
            }
            Constant.DATA_DIR.delete();
        }
        if (Constant.MOVES_DIR.exists()) {
            for (File file : Constant.MOVES_DIR.listFiles()) {
                file.delete();
            }
            Constant.MOVES_DIR.delete();
        }
    }
}
