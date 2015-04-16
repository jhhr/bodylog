package bodylog.files;

import java.io.File;
import java.nio.file.FileSystemException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Static class for storing String values relating to file handling and
 * DateTimeFormatters. Also contains the methods relating to the constants. *
 */
public class Constant {

    /**
     * The statistics folder which contains the folders of movements which
     * contain their session files.
     */
    public static final File DATA_DIR = new File("statistics");

    /**
     * The movements folder which contains the move files that store move data;
     * variables and names.
     */
    public static final File MOVES_DIR = new File("movements");
    /**
     * File ending for session files.
     */
    public static final String SESSION_END = ".ses";

    /**
     * File ending for move files. Not .mov so it's not confused for a video
     * file.
     */
    public static final String MOVE_END = ".moo";

    /**
     * DateTimeFormatter for formatting dates into the form yyyy-mm-dd, suitable
     * as file names as they'll be in chronological order.
     */
    public static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * DateTimeFormatter for formatting dates into the localized form, easier to
     * read for a human.
     */
    public static final DateTimeFormatter UI_DATE_FORMAT = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    /**
     * Converts a string that is in the format used in the UI into the format
     * used for the session files. Used in the UI class SessionEditor.
     *
     * @param dateString date string to be converted
     * @return a date string in the format yyyy-mm-dd throws
     * DateTimeParseException when failing to parse the given string
     * @see bodylog.ui.dataediting.SessionEditor
     */
    public static String uiDateToFileDate(String dateString) {
        return FILE_DATE_FORMAT.format(UI_DATE_FORMAT.parse(dateString));
    }

    /**
     * Creates the movements folder that holds the Move defining move files.
     *
     * @return true if the folder exists after the operation, false otherwise
     * @throws SecurityException when access is denied
     * @see bodylog.files.Constant#MOVES_DIR_NAME
     */
    public static boolean createMovesFolder() throws SecurityException {
        return !Constant.MOVES_DIR.exists() ? Constant.MOVES_DIR.mkdir() : true;
    }

    /**
     * Creates the data folder that holds the Move folders which contain their
     * session files.
     *
     * @return true if the folder exists after the operation, false otherwise
     * @throws SecurityException when access is denied
     * @see bodylog.files.Constant#DATA_DIR_NAME
     */
    public static boolean createDataFolder() throws SecurityException {
        return !Constant.DATA_DIR.exists() ? Constant.DATA_DIR.mkdir() : true;
    }
}
