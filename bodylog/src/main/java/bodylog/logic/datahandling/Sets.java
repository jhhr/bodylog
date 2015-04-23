package bodylog.logic.datahandling;

import bodylog.logic.Set;
import bodylog.logic.Variable;
import bodylog.logic.exceptions.ParsingException;
import java.util.Arrays;

/**
 * Contains all definitions of how Sets are converted to strings and back.
 */
public class Sets {

    //static class, does not have instances
    private Sets() {
    }

    public static final String LINE_PREFIX = "values" + Delimiters.TITLE;

    /**
     * Formats a Set into a single line string.
     *
     * @param set the Set to be formatted
     * @return a string describing the contents of the Set
     */
    public static String format(Set set) {
        String strSet = LINE_PREFIX;
        if (set.size() != 0) {
            for (Object value : set.toArray()) {
                String strValue = Sets.formatValue(value);
                strSet += strValue + Delimiters.VALUE;
            }
            strSet = strSet.substring(0, strSet.length() - 1);
        }
        return strSet;
    }

    /**
     * Parses the given line from a session file and returns a Set with the
     * values from the line.
     *
     * @param line the line to be parsed
     * @param vars the variables from which choices for values are checked
     * @return a Set object populated with values
     * @throws NumberFormatException when parsing to double fails
     * @throws ParsingException when parsing from choices fails
     */
    public static Set parseLine(String line, Variable[] vars) throws
            NumberFormatException, ParsingException {
        Set set = new Set();
        String[] values = line.substring(LINE_PREFIX.length())
                .split(Delimiters.VALUE);
        if (!values[0].isEmpty()) {
            for (int i = 0; i < values.length; i++) {
                set.addValue(parseValue(values[i], vars[i].getChoices()));
            }
        }
        return set;
    }

    /**
     * Converts the given string into an object suitable to be given to a Set.
     * Inverse of formatValue(). Will produce exceptions if the given string
     * won't parse as a double or isn't "null","false" or "true".
     *
     * @param str the string to be manipulated
     * @param choices the choices of strings the value is checked against
     * @return null if given "null false/true if given "false"/"true" double if
     * successfully parsed as double
     * @throws NumberFormatException when parsing to double fails
     * @throws ParsingException when parsing from choices fails
     * @see bodylog.logic.datahandling.Sets#parseLine
     */
    public static Object parseValue(String str, String[] choices)
            throws NumberFormatException, ParsingException {
        if (choices.length != 0) {
            for (String choice : choices) {
                if (str.equals(choice)) {
                    return str;
                }
            }
            throw new ParsingException("Error while parsing Set from string: "
                    + "value ("+str+") is not one of "+Arrays.toString(choices));
        }
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

    /**
     * Converts given value into a String, suitable for writing into session
     * file. Used by the format-method of Set.
     *
     * @param value object to be manipulated
     *
     * @return "null" if value is null, "true"/"false" if value is boolean,
     * "integers are returned as is, trailing zeroes are removed from doubles;
     * that is x.0 becomes "x" but x.y becomes "x.y"
     * @see bodylog.logic.Set
     */
    public static String formatValue(Object value) {
        if (value != null) {
            if (value instanceof Double) {
                double valueD = (Double) value;
                if (valueD == (long) valueD) {
                    return String.format("%d", (long) valueD);
                } else {
                    return String.format("%s", valueD);
                }
            } else {
                return value.toString();
            }
        }
        return null;
    }
}
