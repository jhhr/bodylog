package bodylog.files;

import bodylog.logic.Move;
import bodylog.logic.Set;
import bodylog.logic.Session;
import bodylog.ui.dataediting.SessionEditor;
import java.io.File;
import java.io.FileWriter;

/**
 * Static class for writing things to files. Data from Moves, Sessions and Sets
 * are written.
 */
public class ToFile {

    /**
     * Creates the movements folder that holds the Move defining move files.
     *
     * @throws Exception if creation fails somehow
     * @see bodylog.files.Constant#MOVES_DIR_NAME
     */
    public static void createMovesFolder() throws Exception {
        new File(Constant.MOVES_DIR_NAME).mkdir();
    }

    /**
     * Creates the statistics folder and in it a session folder for the
     * specified movement. The name of the session folder is the Move's name.
     *
     * @param move Move for which a session data folder will be created
     * @throws Exception when creating either folder fails
     * @see bodylog.files.Constant#DATA_DIR_NAME
     */
    public static void createDataFolder(Move move) throws Exception {
        new File(Constant.DATA_DIR_NAME).mkdir();
        new File(Constant.DATA_DIR_NAME + "/" + move).mkdir();
    }

    /**
     * Checks if the specified Move has a move file in the movements folder. The
     * move file has the same name as the Move with added move file specific
     * ending. Used in the UI class MoveEditor.
     *
     * @param move Move used for identifying a single move file whose existence
     * will be checked
     * @return true if the file exists, false otherwise
     * @see bodylog.ui.dataediting.MoveEditor
     * @see bodylog.files.Constant#MOVES_DIR_NAME
     * @see bodylog.files.Constant#MOVE_END
     */
    public static boolean moveFileExists(Move move) {
        return new File(Constant.MOVES_DIR_NAME + "/" + move + Constant.MOVE_END).exists();
    }

    /**
     * Checks if the specified Move has a session file of specified date. The
     * filename of the session file is the same as the given date string with an
     * added session file ending. The file is contained in a folder named after
     * the Move in the statistics folder. Used in the UI class SessionEditor.
     *
     * @param move Move used to identify the folder
     * @param dateStr date string used to identify a single session file whose
     * existence will be checked
     * @return true if the file exists, false otherwise
     * @see bodylog.ui.dataediting.SessionEditor
     * @see bodylog.files.Constant#DATA_DIR_NAME
     * @see bodylog.files.Constant#SESSION_END
     */
    public static boolean sessionFileExists(Move move, String dateStr) {
        return new File(Constant.DATA_DIR_NAME + "/" + move + "/" + dateStr + Constant.SESSION_END).exists();
    }

    /**
     * Writes the variables of the specified Move into a file given the name of
     * the Move plus a move file ending. Used in the UI class MoveEditor.
     *
     * Writes one line per each variable. Nothing is written if no variables are
     * found.
     *
     * @param move Move whose defining data will be saved
     * @throws Exception when writing or creating the file
     * @see bodylog.logic.Move
     * @see bodylog.ui.dataediting.MoveEditor
     * @see bodylog.files.Constant#MOVES_DIR_NAME
     * @see bodylog.files.Constant#MOVE_END
     */
    public static void move(Move move) throws Exception {
        File moveFile = new File(Constant.MOVES_DIR_NAME + "/" + move + Constant.MOVE_END);
        FileWriter writer = new FileWriter(moveFile);
        for (String variable : move.variablesToArray()) {
            writer.write(variable + "\n");
        }
        writer.close();
    }

    /**
     * Writes the data contained in the Sessions of the given Move into session
     * files. Uses a DateTimeFormatter to format the date of Sessions into a
     * string used for the filename of the session files. Used in the UI class
     * SessionEditor.
     *
     * Writes each Session into one file, writing one line per each Set
     * contained in the Session. Nothing is written if no Sets are found. No
     * files are created if no Session are found.
     *
     * @param move Move whose Sessions will be written to files
     * @throws Exception when failing in writing or creating files
     * @see bodylog.logic.Move
     * @see bodylog.logic.Session
     * @see bodylog.logic.Set
     * @see bodylog.ui.dataediting.SessionEditor
     * @see bodylog.files.Constant#FILE_DATE_FORMATTER
     * @see bodylog.files.Constant#DATA_DIR_NAME
     * @see bodylog.files.Constant#SESSION_END
     */
    public static void sessions(Move move) throws Exception {

        for (Session sessio : move.getSessions()) {
            String dateStr = Constant.FILE_DATE_FORMATTER.format(sessio.getDate());
            File sessioTiedosto = new File(Constant.DATA_DIR_NAME + "/" + move + "/" + dateStr + Constant.SESSION_END);
            FileWriter writer = new FileWriter(sessioTiedosto);
            for (Set set : sessio.getSets()) {
                writer.write(set.toString() + "\n");
            }
            writer.close();
        }
    }

}
