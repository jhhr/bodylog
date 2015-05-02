package bodylog.logic.datahandling;

import bodylog.logic.Session;
import bodylog.logic.Set;
import bodylog.logic.Variable;
import bodylog.logic.exceptions.ParsingException;
import bodylog.logic.exceptions.VariableStateException;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;

/**
 * Contains all definitions of how Sessions are converted to strings and back.
 */
public class Sessions {

    //static class, does not have instances
    private Sessions() {
    }

    public static final String VARS_START = "session variables"
            + Delimiters.START + "\n";
    public static final String SETS_START = "sets" + Delimiters.START + "\n";

    /**
     * Parses the entire contents of a session file and returns a Session with
     * the Sets and Variables from the contents.
     *
     * @param scanner the Scanner containing the session file to be parsed
     * @param date the date parsed from the filename of the session file
     *
     * @return a Session populated with Sets
     *
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     *
     * @see bodylog.logic.datahandling.Sets#parseLine
     * @see bodylog.logic.datahandling.Variables#parseLine
     */
    public static Session parse(Scanner scanner, TemporalAccessor date)
            throws ParsingException, VariableStateException {
        Session session = new Session(date);
        scanner.useDelimiter(VARS_START + "|" + SETS_START);
        for (String line : scanner.next().split("\n")) {
            session.addVariable(Variables.parseLine(line));
        }
        for (String line : scanner.next().split("\n")) {
            session.addSet(Sets.parseLine(line, session.getVariables()));
        }
        return session;
    }

    /**
     * Formats a Session into a string of two or more lines.
     *
     * @param session the session to be formatted
     * @return a string describing this Session
     */
    public static String format(Session session) {
        String sesStr = VARS_START;
        for (Variable var : session.getVariables()) {
            sesStr += Variables.format(var) + "\n";
        }
        sesStr += SETS_START;
        for (Set set : session.getSets()) {
            sesStr += Sets.format(set) + "\n";
        }
        //remove last linefeed
        return sesStr.substring(0, sesStr.length() - 1);
    }

}
