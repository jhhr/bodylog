package bodylog.logic.datahandling;

import bodylog.logic.Set;
import bodylog.logic.Variable;
import bodylog.logic.Variable.Type;
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
     * @param vars the variables whose choices for values are checked against
     * the value being parsed
     *
     * @return a Set object populated with values
     *
     * @throws NumberFormatException when parsing to double fails
     * @throws ParsingException when parsing from choices fails
     *
     * @see bodylog.logic.datahandling.Sets#parseValue
     */
    public static Set parseLine(String line, Variable[] vars) throws
            NumberFormatException, ParsingException {
        Set set = new Set();
        String[] values = line.substring(LINE_PREFIX.length())
                .split(Delimiters.VALUE);
        if (!values[0].isEmpty()) {
            for (int i = 0; i < values.length; i++) {
                set.addValue(parseValue(values[i], vars[i]));
            }
        }
        return set;
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
     *
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

    /**
     * Converts the given string into an object suitable to be given to a Set.
     * Inverse of formatValue(). Will produce exceptions if the given string
     * won't parse as a double or isn't "null","false" or "true".
     *
     * @param str the string to be manipulated
     * @param var the variable whose choices the value is checked against
     *
     * @return <ul>
     * <li>null, if given "null" and type is not
     * <code>Type.MANDATORY_CHOICE</code></li>
     * <li>false/true, if given "false"/"true"</li>
     * <li>double, if successfully parsed as double</li>
     * <li>a string matching one of the choices</li>
     * </ul>
     *
     * @throws NumberFormatException when parsing to double fails
     * @throws ParsingException when parsing from choices fails
     *
     * @see bodylog.logic.datahandling.Sets#parseLine
     */
    public static Object parseValue(String str, Variable var)
            throws NumberFormatException, ParsingException {
        Type type = var.getType();
        String addition = "";
        String nullOr = "";
        switch (type) {
            case NUMERICAL:
                try {
                    return Double.parseDouble(str);
                } catch (NumberFormatException nfe) {
                    addition = " failed to parse to double.";
                    break;
                }
            case CHECKBOX:
                switch (str) {
                    case "false":
                        return false;
                    case "true":
                        return true;
                    default:
                        addition = " was not 'true' or 'false'.";
                }
                break;
            case OPTIONAL_CHOICE:
                if (str.equals("null")) {
                    return null;
                } else {
                    nullOr = " 'null' or";
                }
            // no break, continues to next case
            case MANDATORY_CHOICE:
                String[] choices = var.getChoices();
                switch (choices.length) {
                    case 0:
                        addition = " could not be parsed "
                                + "as no choices were found.";
                        break;
                    default:
                        for (String choice : choices) {
                            if (str.equals(choice)) {
                                return str;
                            }
                        }//reaching here means the value wasn't among the choices
                        addition = " is not" + nullOr + " one of "
                                + Arrays.toString(choices);
                }
        }
        throw new ParsingException(
                "Error while parsing Set value from string: "
                + "Variable type was '" + type + "' but "
                + "value ('" + str + "')" + addition);
    }
}
