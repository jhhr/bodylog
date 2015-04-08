package bodylog.util;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Static class for storing important constanst, mostly String values relating
 * to file handling.
 *
 */
public class Constant {

    /**
     * Characters not allowed as the name of Moves or in variables of Moves
     * because they are used in parsing data from files.
     */
    public static final char[] BANNED_CHARS = new char[]{'{', '}', ','};

    /**
     * Checks if the given string contains any of the banned characters. Throws
     * an IllegalArgumentException if so.
     *
     * @param name string to be checked
     */
    public static void nameIsAllowed(String name) {
        for (char ch : BANNED_CHARS) {
            if (name.contains("" + ch)) {
                throw new IllegalArgumentException("the characters {}:, are not allowed");
            }
        }
    }

    /**
     * Name for the folder that will contain the individual data folders for
     * each move which will contain their respective session data files.
     */
    public static final String SESSION_DIR = "statistics";

    /**
     * Name for the folder that contains the move defining files.
     */
    public static final String MOVE_DIR = "movements";

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
     * used for the session files.
     *
     * @param dateString date string to be converted
     * @return a date string in the format yyyy-mm-dd throws
     * DateTimeParseException when failing to parse the given string
     */
    public static String UIDateToFileDate(String dateString) {
        return FILE_DATE_FORMATTER.format(UI_DATE_FORMAT.parse(dateString));
    }
}
