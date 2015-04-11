package bodylog.files;

import bodylog.ui.dataediting.SessionEditor;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Static class for storing String values relating to file handling and
 * DateTimeFormatters. Used by the file handling classes ToFile and FromFile.
 *
 * @see bodylog.files.FromFile
 * @see bodylog.files.ToFile
 */
public class Constant {

    /**
     * Name for the folder that will contain the individual data folders for
     * each move which will contain their respective session data files.
     */
    public static final String DATA_DIR_NAME = "statistics";

    /**
     * Static instance of the statistics folder for convenience.
     */
    public static final File DATA_DIR = new File(Constant.DATA_DIR_NAME);
    
    /**
     * Name for the folder that contains the move defining files.
     */
    public static final String MOVES_DIR_NAME = "movements";

    /**
     * Static instance of the statistics folder for convenience.
     */
    public static final File MOVES_DIR = new File(Constant.MOVES_DIR_NAME);
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
    public static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

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
        return FILE_DATE_FORMATTER.format(UI_DATE_FORMAT.parse(dateString));
    }
}
