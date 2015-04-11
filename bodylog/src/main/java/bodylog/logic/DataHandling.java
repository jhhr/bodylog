package bodylog.logic;

import bodylog.files.FromFile;
import bodylog.ui.tables.EditorTable;

/**
 * Static class for string manipulation. Used for writing Set data to session
 * files and creating Set objects from reading session files and checking names
 * of moves and variables for characters used in parsing move file contents.
 */
public class DataHandling {

    private DataHandling() {
    }

    /**
     * Characters not allowed in the names of Moves as they are used as
     * filenames for the session data folders and move files.
     */
    public static final char[] BANNED_CHARS_MOVE_NAME
            = new char[]{'/', ':', '\\', '*', '?', '"', '<', '>', '|'};

    /**
     * Characters not allowed in variables as they are used parsing data from
     * files into Set values.
     */
    public static final char[] BANNED_CHARS_VARIABLE = new char[]{'{', '}', ','};

    public static String bannedCharsWithSpaces(enum) {
        String chars = "";
        for (char c : BANNED_CHARS_VARIABLE) {
            chars += c + " ";
        }
        return chars.substring(0, chars.length() - 1);
    }

    /**
     * Checks if the given string contains any of the banned characters for move
     * names. if so, throws an IllegalArgumentException. If not, removes
     * extraneous whitespace and returns the modified name. Used in setting the
     * name for a Move.
     *
     * @param name string to be checked
     * @return name with leading and trailing whitespace removed
     * @throws IllegalArgumentException when the name is found to contain
     * illegal characters
     * @see bodylog.logic.Move
     */
    public static String nameIsAllowed(String name) throws IllegalArgumentException {
        for (char ch : BANNED_CHARS_MOVE_NAME) {
            if (name.contains("" + ch)) {
                throw new IllegalArgumentException("the characters "
                        + new String(BANNED_CHARS_MOVE_NAME) + " are not allowed");
            }
        }
        return name.trim();
    }

    /**
     * Checks if the given string contains any of the banned characters for move
     * names. Throws an IllegalArgumentException if so. Used in Move.
     *
     * @param name string to be checked
     * @see bodylog.logic.Move
     */
    public static void variableIsAllowed(String var) {
        for (char ch : BANNED_CHARS_MOVE_NAME) {
            if (var.contains("" + ch)) {
                throw new IllegalArgumentException("the characters " + new String(BANNED_CHARS_VARIABLE) + " are not allowed");
            }
        }
    }

    /**
     * Converts given value into a String, suitable for writing into session
     * file. Used by the toString-method of Set.
     *
     * @param value object to be manipulated
     *
     * @return "null" if value is null, "true"/"false" if value is boolean,
     * "integers are returned as is, trailing zeroes are removed from doubles;
     * that is x.0 becomes "x" but x.y becomes "x.y"
     * @see bodylog.logic.Set
     */
    public static String setValueToString(Object value) {
        if (value != null) {
            if (value.equals(true) || value.equals(false) || value instanceof Integer) {
                return value.toString();
            } else {
                double valueD = (Double) value;
                if (valueD == (long) valueD) {
                    return String.format("%d", (long) valueD);
                } else {
                    return String.format("%s", valueD);
                }
            }
        }
        return null;
    }

    /**
     * Converts given string into an object suitable to be given to a Set.
     * Inverse of setValueToString(). Will produce exceptions if given a string
     * won't parse as a double or isn't "null","false" or "true".
     *
     * @param str String to be manipulated
     * @return null if given "null false/true if given "false"/"true" double if
     * successfully parsed as double
     * @see bodylog.files.FromFile#setForSession
     * @see bodylog.ui.tables.EditorTable#setValueAt
     */
    public static Object stringToSetValue(String str) {
        switch (str) {
            case "null":
                return null;
            case "false":
                return false;
            case "true":
                return true;
            default:
                return Double.parseDouble(str);
        }
    }

}
