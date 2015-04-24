
package bodylog.logic.datahandling;
/**
 * Defines the characters used in parsing and formatting data.
 * @author yonus
 */
public class Delimiters {

    //static class, does not have instances
    private Delimiters() {
    }

    public static final String START = ">";
    public static final String SECTION = "|";
    public static final String TITLE = "=";
    public static final String VALUE = ";";

    public static final char[] CHARS = new char[]{START.charAt(0),
        SECTION.charAt(0), TITLE.charAt(0), VALUE.charAt(0)};
}
