package bodylog.logic;

/**
 * Static class for string manipulation required for writing Set data to session
 * files and creating Set objects from reading session files.
 */
public class DataHandling {

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
        if (value == null) {
            return "null";
        } else if (value.equals(true) || value.equals(false) || value instanceof Integer) {
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
