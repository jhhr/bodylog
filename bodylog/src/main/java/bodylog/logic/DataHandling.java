package bodylog.logic;

/**
 * Static class for string manipulation. Used for writing Set data to session
 * files and creating Set objects from reading session files and checking names
 * of moves and variables for characters used in parsing move file contents.
 */
public class DataHandling {

    private DataHandling() {
    }

    /**
     * Characters not allowed as the name of Moves or in variables of Moves
     * because they are used in parsing data from files.
     */
    public static final char[] BANNED_CHARS = new char[]{'{', '}', ',', '.'};

    /**
     * Checks if the given string contains any of the banned characters. Throws
     * an IllegalArgumentException if so.
     *
     * @param name string to be checked
     */
    public static void nameIsAllowed(String name) {
        for (char ch : BANNED_CHARS) {
            if (name.contains("" + ch)) {
                throw new IllegalArgumentException("the characters " + new String(BANNED_CHARS) + " are not allowed");
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
     * @see bodylog.files.FromFile
     * @see bodylog.ui.tables.EditorTable
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
