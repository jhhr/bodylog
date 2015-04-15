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
     * Lists of characters not allowed in certain places.
     *
     * Names of Moves are used as filenames for the session data folders and
     * move files, so some characters would cause errors.
     *
     * Certain characters are used in parsing data from files into Set values so
     * they are not allowed in variables of Moves.
     */
    public static enum Illegal {

        MOVE_NAME(new char[]{'/', '\\', ':', '*', '?', '"', '<', '>', '|'}),
        VARIABLE(new char[]{'{', '}', ','});

        private final char[] charList;

        Illegal(char[] charList) {
            this.charList = charList;
        }

        public char[] getChars() {
            return charList;
        }
    }

    /**
     * Returns a string of the specified set of illegal characters with a space
     * added between each one for better readability.
     *
     * @param charSet Enumerator <code>Illegal</code> containing the character
     * list
     * @return String constructed from the char list
     */
    public static String IllegalCharsWithSpaces(Illegal charSet) {
        String chars = "";
        for (char c : charSet.getChars()) {
            chars += c + " ";
        }
        return chars.substring(0, chars.length() - 1);
    }

    /**
     * Checks if the given string contains any of the banned characters from the
     * specified character set. If so, throws an IllegalArgumentException. If
     * not, removes extraneous whitespace and returns the modified name. Used in
     * setting the name and variables for a Move.
     *
     * Use <code>Illegal.MOVE_NAME</code> for move name checking and
     * <code>Illegal.VARIABLE</code> for variable checking.
     *
     * @param name string to be checked
     * @return name with leading and trailing whitespace removed
     * @throws IllegalArgumentException when the name is found to contain
     * illegal characters
     * @see bodylog.logic.Move
     * @see bodylog.logic.DataHandling
     */
    public static String nameIsAllowed(String name, Illegal charSet)
            throws IllegalArgumentException {
        for (char ch : charSet.getChars()) {
            if (name.contains("" + ch)) {
                throw new IllegalArgumentException("The characters "
                        + IllegalCharsWithSpaces(charSet) + " are not allowed.");
            }
        }
        return name.trim();
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
